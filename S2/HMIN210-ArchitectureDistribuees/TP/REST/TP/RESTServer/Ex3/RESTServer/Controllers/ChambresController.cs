using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using RESTServer.Models;

namespace RESTServer.Controllers
{
    [Route("api/")]
    [ApiController]
    public class ChambresController : ControllerBase
    {
        private readonly HotelContext _context;

        public ChambresController(HotelContext context)
        {
            _context = context;
            if (_context.Rooms.Count() == 0)
            {

                //add chambre to test 
                
                //chambre 1 with client and reservation
                Chambre c1 = new Chambre(1, 1, 100);
                Client cl1 = new Client(1, "TRAN", "MY");
                Reservation r1 = new Reservation(1, c1.id, cl1.id, DateTime.Now, (DateTime.Now).AddDays(2));
                //r1.confirme = true;//confirmer la reservation
                c1.Reservations.Add(r1);
                _context.Reservations.AddAsync(r1);
                _context.Clients.AddAsync(cl1);
                _context.Rooms.AddAsync(c1);

                _context.SaveChangesAsync();

                Chambre c2 = new Chambre(2, 2, 200);
                Chambre c22 = new Chambre(5, 2, 200);
                Chambre c3 = new Chambre(3, 3, 300);
                Chambre c4 = new Chambre(4, 4, 400);
                _context.Rooms.AddAsync(c22);
                _context.Rooms.AddAsync(c2);
                _context.Rooms.AddAsync(c3);
                _context.Rooms.AddAsync(c4);

                _context.SaveChangesAsync();

                //Create new offer test
                Offre o1 = new Offre(1, 1, DateTime.Now, (DateTime.Now).AddDays(2), 2, 100);
                _context.Offers.AddAsync(o1);
                _context.SaveChangesAsync();
            }
        }

        // GET: api/Rooms
        [HttpGet("Rooms")]
        public async Task<ActionResult<IEnumerable<Chambre>>> GetRooms()
        {
            var list = await _context.Rooms.ToListAsync();

            if (list.Count() == 0)
            {
                return NotFound();
            }
            else
            {   

                for (int i = 0; i < list.Count; i++)
                {   
                    list[i].Reservations = await _context.Reservations.Where(c => c.ChambreId == list[i].id).ToListAsync();
                    foreach (Reservation r in list[i].Reservations)
                    {
                        r.Clients = await _context.Clients.Where(c => c.id == r.ClientId).ToListAsync();
                    }
                }
                return list;
            }
        }

        // GET: api/Rooms/5
        [HttpGet("Rooms/{id}")]
        public async Task<ActionResult<Chambre>> GetRoomByID([FromRoute] int id)
        {

            var chambreRes = await _context.Rooms.FindAsync(id);

            if (chambreRes == null)
            {
                return NotFound();
            }
            else
            {
                chambreRes.Reservations = await _context.Reservations.Where(c => c.ChambreId == id).ToListAsync();
            }

            return Ok(chambreRes);
        }

        // GET: api/Rooms/5
        [HttpGet("Rooms/bed_number={beds}")]
        public async Task<ActionResult<IEnumerable<Chambre>>> GetRoomByType([FromRoute] int beds)
        {

            List<Chambre> chambreRes = await _context.Rooms.Where(x => x.NombreLits == beds).ToListAsync();
            
            if (chambreRes == null)
            {
                return NotFound();
            }
            return chambreRes;

            
        }

        // PUT: api/Rooms/1
        [HttpPut("Rooms/{id}")]
        public async Task<IActionResult> PutRoom(int id, [FromBody] Chambre chambre)
        {
            if (id != chambre.id)
            {
                return BadRequest();
            }

            _context.Entry(chambre).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ChambreExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/Rooms
        [HttpPost("Rooms")]
        public async Task<ActionResult<Chambre>> PostRoom(Chambre chambre)
        {
            _context.Rooms.Add(chambre);
            await _context.SaveChangesAsync();

            return CreatedAtAction(nameof(GetRoomByID), new { id = chambre.id }, chambre);
        }

        // DELETE: api/Rooms/5
        [HttpDelete("Rooms/{id}")]
        public async Task<IActionResult> DeleteChambre([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var chambre = await _context.Rooms.FindAsync(id);
            if (chambre == null)
            {
                return NotFound();
            }

            //delete room
            _context.Rooms.Remove(chambre);
            await _context.SaveChangesAsync();

            //delete reservation
            _context.Reservations.RemoveRange(_context.Reservations.Where(x => x.ChambreId == id));
            await _context.SaveChangesAsync();

            return NoContent();
        }

        [HttpGet("Offers/{identifiant}/{mdp}/{date_debut}/{date_fin}/{bed_quantity}")]
        public async Task<ActionResult<IEnumerable<Offre>>> getOffers(string identifiant, string mdp, string date_debut, string date_fin, int bed_quantity)
        {
            DateTime dt_debut = DateTime.Parse(date_debut);
            DateTime dt_fin = DateTime.Parse(date_fin);
            
            var agenceRes = await _context.Agences.Where(a => a.identifiant == identifiant && a.motdepasse == mdp).ToListAsync();
            if(agenceRes == null)
            {
                return NotFound();
            }

            List<int> listeIndexRoomsUnavailable = new List<int>();
            var reservationsRes = _context.Reservations.Where(r => r.dateEntree > dt_debut && r.dateSortie < dt_fin || (r.dateEntree < dt_debut && r.dateSortie > dt_debut) || (r.dateEntree < dt_fin && r.dateSortie > dt_fin)).ToList();
            foreach(Reservation r in reservationsRes)
            {
                listeIndexRoomsUnavailable.Add(r.ChambreId);
            }

            var list = _context.Rooms.ToList();
            int id_counter = _context.Offers.Count();
            foreach (Chambre c in list)
            {
                if ((!listeIndexRoomsUnavailable.Contains(c.id)) && c.NombreLits == bed_quantity)
                {
                    _context.Offers.Add(new Offre(++id_counter, c.id, dt_debut, dt_fin, (dt_fin - dt_debut).TotalDays, c.Prix));
                    await _context.SaveChangesAsync();
                }
            }
            var ListOffers = await _context.Offers.Where(o => o.dateEntree == dt_debut && o.dateSortie == dt_fin).ToListAsync();
            return ListOffers;
        }

        [HttpGet("Offers/{idO}")]
        public async Task<ActionResult<Offre>> getOffreById(int idO)
        {
            var OffreRes = await _context.Offers.FindAsync(idO);

            if (OffreRes == null)
            {
                return NotFound();
            }

            return Ok(OffreRes);
        }

        private bool ChambreExists(int id)
        {
            return _context.Rooms.Any(e => e.id == id);
        }

    }
}
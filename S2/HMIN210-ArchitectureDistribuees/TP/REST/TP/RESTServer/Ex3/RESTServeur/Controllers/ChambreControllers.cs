using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using RESTServeur.Models;

namespace RESTServeur.Controllers
{   
    [Route("api/")]
    [ApiController]
    class ChambreControllers : ControllerBase
    {
        private readonly HotelContext _context;

        //constructor
        public ChambreControllers(HotelContext context)
        {
            _context = context;
            if(_context.Rooms.Count() == 0)
            {
                //add chambre to test 
                Chambre c1 = new Chambre(2, 200);
                Client cl1 = new Client("TRAN", "MY");
                Reservation r1 = new Reservation(c1, cl1, DateTime.Now, (DateTime.Now).AddDays(2));
                c1.Reservations.Add(r1);
                _context.Reservations.AddAsync(r1);
                _context.Clients.AddAsync(cl1);
                _context.Rooms.AddAsync(c1);

                _context.SaveChangesAsync();
            }
        }

        //get all rooms
        [HttpGet("rooms")]
        public async Task<ActionResult<IEnumerable<Chambre>>> GetRoomsTest()
        {
            var list = await _context.Rooms.ToListAsync();

            if(list.Count() == 0)
            {
                return NotFound();
            }
            else
            {
                for(int i=0; i<list.Count; i++)
                {
                    list[i].Reservations = await _context.Reservations.Where(c => c.ID == list[i].ID).ToListAsync();
                }
                return list;
            }
        }
    }
}

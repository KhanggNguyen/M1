using System;
using System.Collections.Generic;
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
    public class ReservationsController : ControllerBase
    {
        private readonly HotelContext _context;

        public ReservationsController(HotelContext context)
        {
            _context = context;
        }

        // GET: api/Reservations
        [HttpGet("Reservations")]
        public async Task<ActionResult<IEnumerable<Reservation>>> GetReservations()
        {
            return await _context.Reservations.ToListAsync();
        }

        // GET: api/Reservations/5
        [HttpGet("Reservations/{rid}")]
        public async Task<ActionResult<Reservation>> GetReservationById([FromRoute] int rid)
        {
            var reservation = await _context.Reservations.FindAsync(rid);

            if (reservation == null)
            {
                return NotFound();
            }

            return Ok(reservation);
        }
  
        [HttpGet("Reservations/Rooms/{rid}")]
        public async Task<ActionResult<IEnumerable<Reservation>>> GetReservationsOfRoom([FromRoute] int rid)
        {
                var reservationRes = await _context.Reservations.Where(x => x.ChambreId == rid).ToListAsync();
                if(reservationRes == null)
                {
                    return NotFound();
                }
                return reservationRes;
        }

        // PUT: api/Reservations/5
        [HttpPut("Reservations/{rid}")]
        public async Task<ActionResult<Reservation>> PutReservation([FromRoute] int rid, [FromBody] Reservation reservation)
        {
            Reservation ReservationRes = await _context.Reservations.FindAsync(reservation.id);
            if(ReservationRes == null)
            {
                return NotFound();
            }
            ReservationRes = reservation;
            await _context.SaveChangesAsync();

            return reservation;
        }

        // Post: api/Reservations
        [HttpPost("Reservations/")]
        public async Task<ActionResult<Reservation>> MakeReservation(Reservation reservation)
        {
            _context.Reservations.Add(reservation);
            //_context.Reservations.Add(r);
            await _context.SaveChangesAsync();

            return Ok(reservation);
        }

        // DELETE: api/Reservations/5
        [HttpDelete("Reservations/{rid}")]
        public async Task<IActionResult> DeleteReservation([FromRoute] int rid)
        {
            var reservation = await _context.Reservations.FindAsync(rid);
            if (reservation == null)
            {
                return NotFound();
            }

            _context.Reservations.Remove(reservation);
            await _context.SaveChangesAsync();

            return Ok(reservation);
        }

        private bool ReservationExists(int id)
        {
            return _context.Reservations.Any(e => e.id == id);
        }
    }
}
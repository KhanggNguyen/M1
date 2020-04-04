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
    public class AgencesController : ControllerBase
    {
        private readonly HotelContext _context;

        public AgencesController(HotelContext context)
        {
            _context = context;

            Agence a1 = new Agence(1, "Je suis agence A", 1.10, "agence1", "agence1");
            _context.Agences.AddAsync(a1);

            _context.SaveChangesAsync();
        }

        // GET: api/Agences
        [HttpGet("Agences")]
        public async Task<ActionResult<IEnumerable<Agence>>> GetAgences()
        {
            return await _context.Agences.ToListAsync();
        }

        // GET: api/Agences/5
        [HttpGet("Agences/{id}")]
        public async Task<ActionResult<Agence>> GetAgenceById([FromRoute] int id)
        {
            var agence = await _context.Agences.FindAsync(id);

            if (agence == null)
            {
                return NotFound();
            }

            return Ok(agence);
        }

        // PUT: api/Agences/5
        [HttpPut("Agences/{id}")]
        public async Task<IActionResult> PutAgence([FromRoute] int id, [FromBody] Agence agence)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != agence.id)
            {
                return BadRequest();
            }

            _context.Entry(agence).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!AgenceExists(id))
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

        // POST: api/Agences
        [HttpPost("Agences")]
        public async Task<ActionResult<Agence>> PostAgence([FromBody] Agence agence)
        {
            _context.Agences.Add(agence);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetAgence", new { id = agence.id }, agence);
        }

        // DELETE: api/Agences/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteAgence([FromRoute] int id)
        {
            var agence = await _context.Agences.FindAsync(id);
            if (agence == null)
            {
                return NotFound();
            }

            _context.Agences.Remove(agence);
            await _context.SaveChangesAsync();

            return Ok(agence);
        }

        [HttpPost("Agences/Connexion")]
        public async Task<ActionResult<Agence>> Authentification(Agence agence)
        {
            var agenceRes = await _context.Agences.Where(a => a.identifiant == agence.identifiant && a.motdepasse == agence.motdepasse).ToListAsync();
            if (agenceRes == null)
                return NotFound();

            return Ok(agenceRes);
        }

        private bool AgenceExists(int id)
        {
            return _context.Agences.Any(e => e.id == id);
        }
    }
}
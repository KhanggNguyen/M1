using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using MySql.Data.EntityFrameworkCore;

namespace RESTServeur.Models
{
    class HotelContext : DbContext
    {
        public DbSet<Chambre> Rooms { get; set; }
        public DbSet<Reservation> Reservations { get; set; }
        public DbSet<Offre> Offers { get; set; }
        public DbSet<Agence> Agences { get; set; }
        public DbSet<Client> Clients { get; set; }

        public HotelContext(DbContextOptions<HotelContext> options) : base(options)
        {

        }
    }
}

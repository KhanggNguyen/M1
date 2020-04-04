using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RESTServer.Models
{
    public class Offre
    {
        private static int id_counter = 1;
        public int id { get; private set; }
        public int chambreId { get; set; }
        public DateTime dateEntree { get; set; }
        public DateTime dateSortie { get; set; }
        public double prix { get; set; }

        public Offre()
        {
            this.id = id_counter;
            id_counter++;
            chambreId = -1;
            dateEntree = DateTime.Now;
            dateSortie = DateTime.Now;
            prix = 0;
        }
        public Offre(int idO, int cId, DateTime date_db, DateTime date_fin, double nb_nuits, double prixUnite)
        {
            id = id_counter;
            id_counter++;
            id = idO;
            chambreId = cId;
            dateEntree = date_db;
            dateSortie = date_fin;
            prix = prixUnite * nb_nuits;
        }
    }
}
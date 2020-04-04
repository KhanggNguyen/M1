using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;

namespace RESTClient
{
    public class Offre
    {
        private static int id_counter = 2;//parce qu'on a un exemple de base dans le serveur
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
        public Offre(int cId, DateTime date_db, DateTime date_fin, double nb_nuits, double prixUnite)
        {
            id = id_counter;
            chambreId = cId;
            dateEntree = date_db;
            dateSortie = date_fin;
            prix = prixUnite * nb_nuits;
        }

        public override string ToString()
        {
            string offer = "L'identifiant : " + id + Environment.NewLine + "L'identifiant de la chambre : " + chambreId + Environment.NewLine + "Date entrée : " + dateEntree.ToString() + Environment.NewLine + "Date départ : " + dateSortie.ToString() + Environment.NewLine + "Prix : " + prix;
            StringBuilder builder = new StringBuilder();
            builder.Append(offer);

            return builder.ToString();

        }
    }
}
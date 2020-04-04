using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;

namespace RESTServer.Models
{
    public class Reservation
    {
        public int id { get; private set; }
        public int ChambreId { get; set; }
        public int ClientId { get; set; }
        public ICollection<Client> Clients { get; set; }
        public DateTime dateEntree { get; set; }
        public DateTime dateSortie { get; set; }
        public double nombreNuits { get; set; }
        public bool confirme { get; set; }

        public Reservation()
        {
            id = -1;
            ChambreId = -1;
            ClientId = -1;
            Clients = new List<Client>();
            dateEntree = DateTime.Now;
            dateSortie = DateTime.Now;
            nombreNuits = 1;
            confirme = false;
        }

        public Reservation(int idR, int ch, int cl, DateTime d1, DateTime d2)
        {
            id = idR;
            ChambreId = ch;
            ClientId = cl;
            Clients = new List<Client>();
            dateEntree = d1;
            dateSortie = d2;
            nombreNuits = (d2-d1).TotalDays;//calculer total dates réservé
            confirme = false;
        }

        public override string ToString()
        {
            string reservation = "L'identifiant : " + id + Environment.NewLine + "Client id : " + ClientId + Environment.NewLine + "Date Entree : " + dateEntree.ToString() + Environment.NewLine + "Date sortie : " + dateSortie.ToString() + Environment.NewLine + "Confirmation : " + confirme;
            StringBuilder builder = new StringBuilder();
            builder.Append(reservation);

            return builder.ToString();
        }
    }
}
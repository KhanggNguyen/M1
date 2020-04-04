using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;

namespace RESTServer.Models
{
    public class Chambre
    {
        public int id { get; set; }
        public ICollection<Reservation> Reservations { get; set; }
        public int NombreLits { get; set; }
        public float Prix { get; set; }
        //public string file_name { get; set; }
        
        public Chambre()
        {
            id = -1;
            Reservations = new List<Reservation>();
            NombreLits = 0;
            Prix = 0;
        }

        public Chambre(int idR, int nbrL, float p)
        {
            id = idR;
            //file_name = fn;
            Reservations = new List<Reservation>();
            NombreLits = nbrL;
            Prix = p;
        }

        public Chambre(int idR, int nbrL, float p, List<Reservation> listR)
        {
            id = idR;
            Reservations = listR;
            NombreLits = nbrL;
            Prix = p;
        }

        public override string ToString()
        {
            string chambre = "L'identifiant : " + id + Environment.NewLine + "Nombre lits : " + NombreLits + Environment.NewLine + "Prix : " + Prix;
            StringBuilder builder = new StringBuilder();
            builder.Append(chambre);

            return builder.ToString();
        }
    }
}
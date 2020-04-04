using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;


namespace RESTServer.Models
{
    public class Client
    {
        public int id { get; set; }
        //public ICollection<Reservation> mesReservation { get; set; }
        public string nom { get; set; }
        public string prenom { get; set; }

        public Client()
        {
            id = -1;
            //mesReservation = new List<Reservation>();
            nom = null;
            prenom = null;
        }

        public Client(int idCl, string n, string pn)
        {
            id = idCl;
            //mesReservation = new List<Reservation>();
            nom = n;
            prenom = pn;
        }

    }
}
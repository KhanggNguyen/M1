using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RESTServeur.Models
{
    public class Reservation : IDisposable
    {
        private static List<bool> UsedCounter = new List<bool>();
        private static object Lock = new object();
        public int ID { get; private set; }
        public Chambre chambre { get; set; }
        public string infoClient { get; set; }
        public Client client { get; set; }
        public DateTime dateEntree { get; set; }
        public DateTime dateSortie { get; set; }
        public double nombreNuits { get; set; }
        public bool confirme { get; set; }
        public Reservation()
        {

        }

        public Reservation(Chambre ch, Client cl, DateTime d1, DateTime d2)
        {
            //lock pour cas multi-thread
            lock (Lock)
            {
                //initialize new index ID
                int nextIndex = GetAvailableIndex();
                if (nextIndex == -1)
                {
                    nextIndex = UsedCounter.Count;
                    UsedCounter.Add(true);
                }
                ID = nextIndex;
            }
            chambre = ch;
            client = cl;
            //infoClient = info;
            dateEntree = d1;
            dateSortie = d2;
            nombreNuits = (d2-d1).TotalDays;//calculer total dates réservé
            confirme = false;
        }

        public void Dispose()
        {
            lock (Lock)
            {
                UsedCounter[ID] = false;
            }
        }

        private int GetAvailableIndex()
        {
            for (int i = 0; i < UsedCounter.Count; i++)
            {
                if (UsedCounter[i] == false)
                {
                    return i;
                }
            }

            // Nothing available.
            return -1;
        }
    }
}
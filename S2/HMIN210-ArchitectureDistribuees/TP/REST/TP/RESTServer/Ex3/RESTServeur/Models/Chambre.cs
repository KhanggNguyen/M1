using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RESTServeur.Models
{
    public class Chambre : IDisposable
    {
        private static List<bool> UsedCounter = new List<bool>();
        private static object Lock = new object();
        public int ID { get; private set; }
        public List<Reservation> Reservations { get; set; }
        public int NombreLits { get; set; }
        public float Prix { get; set; }
        //public string file_name { get; set; }
        
        public Chambre()
        {

        }

        public Chambre(int nbrL, float p)
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
            //file_name = fn;
            Reservations = new List<Reservation>();
            NombreLits = nbrL;
            Prix = p;
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
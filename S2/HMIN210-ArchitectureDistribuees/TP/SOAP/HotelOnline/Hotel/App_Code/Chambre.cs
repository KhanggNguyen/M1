using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Hotel.App_Code
{
    public class Chambre : IDisposable
    {
        private static List<bool> UsedCounter = new List<bool>();
        private static object Lock = new object();
        public int ID { get; private set; }
        public List<Reservation> mesReservation { get; set; }
        public int nombreLits { get; set; }
        public float prix { get; set; }
        public string file_name { get; set; }
        public Hotel hotel { get; set; }
        
        public Chambre()
        {

        }

        public Chambre(int nbrL, float p, Hotel h, string fn)
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
            file_name = fn;
            mesReservation = new List<Reservation>();
            nombreLits = nbrL;
            prix = p;
            hotel = h;
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
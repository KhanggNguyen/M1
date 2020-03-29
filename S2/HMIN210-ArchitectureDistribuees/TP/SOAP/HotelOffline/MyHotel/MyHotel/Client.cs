using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MyHotel
{
    public class Client
    {
        private static List<bool> UsedCounter = new List<bool>();
        private static object Lock = new object();
        public int ID { get; private set; }
        public List<CB> mesCB { get; set; }
        public List<Reservation> mesReservation { get; set; }
        public string nom { get; set; }
        public string prenom { get; set; }

        public Client(string n, string pn)
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
            mesCB = new List<CB>();
            mesReservation = new List<Reservation>();
            nom = n;
            prenom = pn;
        }

        public void addCB(CB cb)
        {
            if (!mesCB.Contains(cb))
            {
                mesCB.Add(cb);
            }
            else
            {
                Console.Error.WriteLine("Cette carte a été déjà ajouté dans la liste!");
            }
            
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
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Hotel.App_Code
{
    public class Agence : IDisposable
    {
        private static List<bool> UsedCounter = new List<bool>();
        private static object Lock = new object();
        public int ID { get; set; }
        public string identifiant { get; set; }
        public string motdepasse { get; set; }
        public string nomAgence { get; set; }
        public double tarifPropre { get; set; }

        public Agence()
        {

        }
        
        public Agence(string n, double tp, string id, string mdp)
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

                nomAgence = n;
                tarifPropre = tp;
                identifiant = id;
                motdepasse = mdp;
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
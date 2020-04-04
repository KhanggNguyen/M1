using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RESTServeur.Models
{
    public class Offre : IDisposable
    {
        private static List<bool> UsedCounter = new List<bool>();
        private static object Lock = new object();
        public int ID { get; private set; }
        public Chambre chambre { get; set; }
        public DateTime dateEntree { get; set; }
        public DateTime dateSortie { get; set; }
        public double prix { get; set; }

        public Offre()
        {

        }
        public Offre(Chambre c, DateTime date_db, DateTime date_fin, double nb_nuits)
        {
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
            chambre = c;
            dateEntree = date_db;
            dateSortie = date_fin;
            prix = chambre.Prix * nb_nuits;
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
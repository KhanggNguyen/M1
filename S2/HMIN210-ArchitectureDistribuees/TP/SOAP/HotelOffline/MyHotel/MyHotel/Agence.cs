using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MyHotel
{
    public class Agence
    {
        private static List<bool> UsedCounter = new List<bool>();
        private static object Lock = new object();
        public int ID { get; private set; }
        private string identifiant { get; set; }
        private string motdepasse { get; set; }
        public List<Hotel> listeHotels = new List<Hotel>();
        public string nomAgence { get; set; }
        public double tarifPropre { get; set; }
        
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

        public void addHotel(Hotel h)
        {
            if (!listeHotels.Contains(h))
            {
                listeHotels.Add(h);
            }
        }

        public List<Hotel> getHotels()
        {
            foreach(Hotel h in listeHotels)
            {
                Console.WriteLine("Hotel nom : " + h.nomHotel + " - Adresse [ " + h.adresseHotel.ToString() + " ] " + " - Prix à payer : " + h.mesChambres[0].prix * (1+tarifPropre) + " - Lits proposé : " + h.mesChambres[0].nombreLits);
            }
            return listeHotels; 
        }
    }
}
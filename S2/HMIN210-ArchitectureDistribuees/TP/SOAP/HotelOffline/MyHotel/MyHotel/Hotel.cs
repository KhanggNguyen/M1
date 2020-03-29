using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MyHotel
{
    public class Hotel : IDisposable
    {
        private static List<bool> UsedCounter = new List<bool>();
        private static object Lock = new object();

        public int ID { get; private set; }
        public List<Chambre> mesChambres { get; set; }
        public string nomHotel { get; set; }
        public Adresse adresseHotel { get; set; }
        public float nombreEtoile { get; set; }
        public Hotel(string nom, Adresse adr, float nbrE)
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
            

            mesChambres = new List<Chambre>();
            nomHotel = nom;
            adresseHotel = adr;
            nombreEtoile = nbrE;
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

        public void addChambre(Chambre c)
        {
            mesChambres.Add(c);
        }

    }
}
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MyHotel
{
    public class Adresse
    {
        private static List<bool> UsedCounter = new List<bool>();
        private static object Lock = new object();

        public int ID { get; private set; }
        public string pays { get; set; }
        public string ville { get; set; }
        public string rue { get; set; }
        public string numero { get; set; }
        public string lieudit { get; set; }
        public string positionGPS { get; set; }

        public Adresse(string p, string v, string r, string n, string ld, string pGPS)
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
            pays = p;
            ville = v;
            rue = r;
            numero = n;
            lieudit = ld;
            positionGPS = pGPS;
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
        

        public override String ToString()
        {
            return "pays : " + pays + " - ville : " + ville + " - numero : " + numero + " - rue : " + rue + " - lieu dit : " + lieudit;
        }
    };
}
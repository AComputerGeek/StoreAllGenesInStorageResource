import edu.duke.*; 

/**
* 
* @author: Amir Armion 
* 
* @version: V.01
* 
*/
public class StoreAllGenesInStorageResource
{
    StorageResource genes = new StorageResource();

    public String findGene(String dna)
    {
        String gene       = "";
        int    startCodon = dna.indexOf("ATG");

        if(startCodon == -1)
        {
            return gene;
        }

        int taaIndex  = findStopCodon(dna, startCodon, "TAA");
        int tgaIndex  = findStopCodon(dna, startCodon, "TGA");
        int tagIndex  = findStopCodon(dna, startCodon, "TAG");

        int min1      = Math.min(taaIndex, tgaIndex);
        int stopCodon = Math.min(tagIndex, min1);

        if(stopCodon != dna.length())
        {
            return dna.substring(startCodon, stopCodon + 3);
        }
        else
        {
            return gene;
        }
    }

    public StorageResource getAllGenes(String dna)
    {
        int    startIndex = 0;
        int    startCodon = dna.indexOf("ATG");
        int    stopCodon  = 0;
        String gene       = "";

        if(startCodon == -1)
        {
            System.out.println("No Gene!");
        }

        while(startIndex < dna.length())
        {
            startCodon = dna.indexOf("ATG", startIndex);

            if(startCodon == -1)
            {
                break;
            }

            int taaIndex = findStopCodon(dna, startCodon, "TAA");
            int tgaIndex = findStopCodon(dna, startCodon, "TGA");
            int tagIndex = findStopCodon(dna, startCodon, "TAG");

            if (taaIndex == -1 && tgaIndex == -1 && tagIndex == -1)
            {
                System.out.println("No Gene!");
            }
            else if (taaIndex != -1 && tgaIndex != -1 && tagIndex != -1)
            {
                stopCodon = Math.min(taaIndex, Math.min(tgaIndex, tagIndex));
            }
            else if (taaIndex == -1 && tgaIndex != -1 && tagIndex != -1)
            {
                stopCodon = Math.min(tgaIndex, tagIndex);
            }
            else if (taaIndex != -1 && tgaIndex != -1 && tagIndex == -1)
            {
                stopCodon = Math.min(taaIndex, tgaIndex);
            }
            else if (taaIndex != -1 && tgaIndex == -1 && tagIndex != -1)
            {
                stopCodon = Math.min(taaIndex, tagIndex);
            }
            else if (taaIndex == -1 && tgaIndex == -1 && tagIndex != -1)
            {
                stopCodon = tagIndex;
            }
            else if (taaIndex == -1 && tgaIndex != -1 && tagIndex == -1)
            {
                stopCodon = tgaIndex;
            }
            else if (taaIndex != -1 && tgaIndex == -1 && tagIndex == -1)
            {
                stopCodon = taaIndex;
            }

            if ((stopCodon != -1) && (stopCodon <= (dna.length() - 3)))
            {
                genes.add(dna.substring(startCodon, stopCodon + 3));
                startIndex = stopCodon + 3;
            }
            else
            {
                break;
            }
        }

        return genes;
    }

    public int findStopCodon(String dna, int startCodon, String stopCodonPattern)
    {
        int stopCodon = dna.indexOf(stopCodonPattern, startCodon + 3);

        while(stopCodon != -1)
        {
            if((stopCodon - startCodon) % 3 == 0)
            {
                return stopCodon;
            }
            else
            {
                stopCodon = dna.indexOf(stopCodonPattern, stopCodon + 1);
            }
        }

        return dna.length();
    }

    public void testGenes()
    {
        genes.clear();
        getAllGenes("ATGHHHTAAHATGGGCGHHATGCCCTAGT");

        for(String gene: genes.data())
        {
            System.out.println(gene);
        }

        System.out.println("Genes Storage size is: " + genes.size());
    }
}

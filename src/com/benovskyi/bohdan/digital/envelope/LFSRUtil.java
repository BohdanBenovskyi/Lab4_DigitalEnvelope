package com.benovskyi.bohdan.digital.envelope;

public class LFSRUtil {
	private boolean[] lfsr;
    private int tap;

    public LFSRUtil(String seed, int tap)
    {
        lfsr = new boolean[seed.length()];
        this.tap = (seed.length()-1)-tap;

        for(int i = 0; i < seed.length(); i++)
        {
            if(seed.charAt(i) == 48)
            {
                lfsr[i] = false;
            }
            else
            {
                lfsr[i] = true;
            }
        }
    }

    public int step()
    {
        boolean newBit = lfsr[0] ^ lfsr[tap];

        for(int i = 0; i < lfsr.length-1; i++)
        {
            lfsr[i] = lfsr[i+1];
        }
        lfsr[lfsr.length-1] = newBit;

        return newBit == false ? 0 : 1;
    }

    public int generate(int k)
    {
        int temp = 0;

        for(int i = 0; i < k; i++)
        {
            temp *= 2;
            temp += step();
        }

        return temp;
    }

    public String toString()
    {
        String representation = "";
        for(int i = 0; i < lfsr.length; i++)
        {
            representation += lfsr[i] == false ? 0 : 1;
        }
        return representation;
    }
}

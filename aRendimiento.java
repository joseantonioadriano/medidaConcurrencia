import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.Date;
import java.lang.*;

public class aRendimiento 
{
	static AtomicLong iatom= new AtomicLong(0);
	static Object o= new Object();
	static long isync= 0;
	static ReentrantLock l= new ReentrantLock();
	static long ireen= 0;
	static int iC, iS, iR;
	
	public static class aRendAtom extends Thread
	{
		public void run()
		{
			iatom.incrementAndGet();
		}
	}
	
	
	public static class aRendReent extends Thread
	{		
		public void run()
		{
			l.lock();
				ireen++;
			l.unlock();
		}
	}	
	
	public static class aRendSync extends Thread
	{		
		public void run()
		{
			synchronized(o)
			{
				isync++;
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		final int nIter= 10000;
		long iniCrono, finCrono, acumCrono;
		int iter;
		
		System.out.print("Iteraciones:   ");
		iter= 0;		
		while(iter < nIter)
		{
			iter+= 1000;
			System.out.print(iter+"       ");
		}
		System.out.println();
		
		/*inicio de medida de atomic*/
		aRendAtom hatom[]= new aRendAtom[nIter];
		
		iter= 0;
		iniCrono= 0;
		finCrono= 0;
		acumCrono= 0;
		System.out.print("Atomic:      ");
		while(iter < nIter)
		{
			iniCrono= System.currentTimeMillis();
				for(int i= 0; i< nIter; i++)
					hatom[i]= new aRendAtom();

				for(int i= 0; i< nIter; i++)
					hatom[i].start();			

				for(int i= 0; i< nIter; i++)
					hatom[i].join();
			finCrono= System.currentTimeMillis();
			acumCrono+= finCrono-iniCrono;
			System.out.print(acumCrono+ "(ms)   ");
		iter+= 1000;
		}
		System.out.println();
		/*fin de medida de atomic*/
		
		/*inicio de medida de sync*/
		aRendSync hsync[]= new aRendSync[nIter];
		
		iter= 0;
		iniCrono= 0;
		finCrono= 0;
		acumCrono= 0;
		System.out.print("Synchr:      ");
		while(iter < nIter)
		{
			iniCrono= System.currentTimeMillis();
				for(int i= 0; i< nIter; i++)
					hsync[i]= new aRendSync();

				for(int i= 0; i< nIter; i++)
					hsync[i].start();			

				for(int i= 0; i< nIter; i++)
					hsync[i].join();
			finCrono= System.currentTimeMillis();
			acumCrono+= finCrono-iniCrono;
			System.out.print(acumCrono+ "(ms)   ");
		iter+= 1000;
		}
		System.out.println();
		/*fin de medida de sync*/

		/*inicio de medida de reentrant*/
		aRendReent hreent[]= new aRendReent[nIter];
		
		iter= 0;
		iniCrono= 0;
		finCrono= 0;
		acumCrono= 0;
		System.out.print("Reentr:      ");
		while(iter < nIter)
		{
			iniCrono= System.currentTimeMillis();
				for(int i= 0; i< nIter; i++)
					hreent[i]= new aRendReent();

				for(int i= 0; i< nIter; i++)
					hreent[i].start();			

				for(int i= 0; i< nIter; i++)
					hreent[i].join();
			finCrono= System.currentTimeMillis();
			acumCrono+= finCrono-iniCrono;
			System.out.print(acumCrono+ "(ms)   ");
		iter+= 1000;
		}
		System.out.println();
		/*fin de medida de reentrant*/
		
	}
}
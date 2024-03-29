import java.io.* ;
import java.util.*;
import OMDB.src.mashup.*;


/*cette classe permet de parser les fichiers csv*/
public class Le_fameux 
{
	/*listes pour les films de Montpellier et Paris*/
	private ArrayList<recup_ligne_Montpellier> listMont;
	private ArrayList<recup_ligne_Paris> listParis;
	
	
	
	
	public Le_fameux()
	{
		listMont =new ArrayList<recup_ligne_Montpellier>();
		listParis=  new 	ArrayList<recup_ligne_Paris>();
	}
	
	/*parser de Montpellier**/
	public void parserMontpellier(String path_file)
	{
		
		  File f = new File (path_file);
		 
	    String line ;
		try
		{
			FileReader fr = new FileReader (f);
		    BufferedReader br = new BufferedReader (fr);
			line = br.readLine();
			 
		        while (line != null)
		        {
		            //System.out.println (line);
		        	
		            line = br.readLine();
		            if(line !=null)
		            {
		            	//System.out.println(line);
		            	//System.out.println("");
		            	String[] tab = line.split(";",12);
		            	Date d= parseDate( tab[3]);
		            	//System.out.println("------ "+ tab.length);
		            	recup_ligne_Montpellier obj1 = new recup_ligne_Montpellier(tab[0], tab[1],  Integer.parseInt(tab[2]), d, tab[4], tab[5],tab[6],tab[7], tab[8], tab[9]);//, tab[10], tab[11]
		            	if(tab.length>10)
		            	{
		            		obj1.setUrl_description(tab[10]);
		            	}
		            	listMont.add(obj1) ;
		            }
		       
		            
		           }
		        br.close();
	            fr.close();
			 
		} catch(FileNotFoundException e )
		{
			 e.printStackTrace();
		} catch(Exception e )
		{
			 e.printStackTrace();
		} 
	}
	
	public int ParIntPerso(String nb) 
	{
		try {
			return Integer.parseInt(nb);
		} catch (NumberFormatException e) {
			 return -1 ;
		}
	}
	
	@SuppressWarnings("deprecation")
	public Date parseDate (String D)
	{
		try {
			 String[] Date_deb = D.split("/");
	            Date d= new Date ( Integer.parseInt(Date_deb[2]), Integer.parseInt(Date_deb[1]), Integer.parseInt(Date_deb[0]));
		return d;
		} catch (Exception e) {
			return null;
		}
	}
	 
	
	/*parser paris*/
	@SuppressWarnings("deprecation")
	public void parserParis(String path_file)
	{
		//Integer.parseInt
		  File f = new File (path_file);
		 
	    String line ;
		try
		{
			FileReader fr = new FileReader (f);
		    BufferedReader br = new BufferedReader (fr);
			line = br.readLine();
			 
		        while (line != null)
		        {
		            //System.out.println (line);
		        	
		            line = br.readLine();
		            if (line !=null){
		            //System.out.println(line);
		            String[] tab = line.split(";");
		            //System.out.println(tab.length);
		            String[] Date_deb = tab[2].split("-");
		            String[] Date_fin = tab[3].split("-");
		            Date d_deb= new Date ( Integer.parseInt(Date_deb[0]), Integer.parseInt(Date_deb[1]), Integer.parseInt(Date_deb[2]));
		            Date d_fin= new Date ( Integer.parseInt(Date_fin[0]), Integer.parseInt(Date_fin[1]), Integer.parseInt(Date_fin[2]));
		            
		            recup_ligne_Paris obj1 = new recup_ligne_Paris(tab[0], tab[1], d_deb, d_fin, tab[4], tab[5], tab[6], ParIntPerso(tab[7]), tab[8]);
		            
		            if (tab.length>9)//si on a des coordonnées 
		            {
		            	 //System.out.println(tab[9]);
		            	String[] tab_corrd = tab[9].split(",");
		            	obj1.addCoord( Float.valueOf(tab_corrd [0]), Float.valueOf(tab_corrd[1] ));
		            }
		            listParis.add(obj1) ;
		            }
		        }
		        	 
		       br.close();
		       fr.close();
			 
		} catch(FileNotFoundException e )
		{
			 e.printStackTrace();
		} catch(Exception e )
		{
			 e.printStackTrace();
		} 
	}
	
	
	
	/*Des jolies getteurs et setteurs */
	public ArrayList<recup_ligne_Montpellier> getListMont() {
		return listMont;
	}

	public void setListMont(ArrayList<recup_ligne_Montpellier> listMont) {
		this.listMont = listMont;
	}

	public ArrayList<recup_ligne_Paris> getListParis() {
		return listParis;
	}

	public void setListParis(ArrayList<recup_ligne_Paris> listParis) {
		this.listParis = listParis;
	}
	
	
	
	
	//test des parsers and others
	public static void main(String[] args) {
		
		Le_fameux parser = new Le_fameux(); 
		parser.parserParis("/home/abdi/java_workspace/TP_Web_Semantique/Paris.csv"); //chemin à adapter à l'execution 
		parser.parserMontpellier("/home/abdi/java_workspace/TP_Web_Semantique/VilleMTP_MTP_FilmsTournes (1).csv");
		
		Iterator<recup_ligne_Montpellier> it =parser.getListMont().iterator()  ;  
		OMDBProxy obj = new OMDBProxy();
		
	
		while(it.hasNext())
		{
		
			recup_ligne_Montpellier ligne =it.next();
			HashMap<String,String> retour =obj.getMovieInfos(ligne.getTitre());
			
			System.out.println(retour.get("Runtime"));
		}
		System.out.println("done");
	}

}

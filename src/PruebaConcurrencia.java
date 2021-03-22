import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

class Concurrencia extends JFrame implements ActionListener{
	ArrayList<String> datos = new ArrayList<String>();
	JButton start;
	JTextArea indicesSi, indicesNo, numConteoSi, numConteoNo, numPorcentajeSi, numPorcentajeNo;
	JProgressBar pgsBar, siBar, noBar;
	
	public Concurrencia(){
		
		getContentPane().setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Concurrencia");
		setSize(800,500);
		setLocationRelativeTo(null);
		setVisible(true);
		
		datos = new ArrayList<String>();//instancia datos
		for (int i = 0; i < 10000000; i++) {//generacion datos
			if ((int)(Math.round(Math.random()))==0) {//importante usar round porque sino salen casi puros ceros
				datos.add("No");
			}else {
				datos.add("Si");
			}
		}
		
		JLabel resultadosSi = new JLabel("Resultados Si");
		resultadosSi.setBounds(42, 36, 170, 25);
		add(resultadosSi);
		
		JLabel resultadosNo = new JLabel("Resultados No");
		resultadosNo.setBounds(290, 36, 170, 25);
		add(resultadosNo);
		
		JLabel conteoSi = new JLabel("Conteo: ");
		conteoSi.setBounds(42, 75, 90, 25);
		add(conteoSi);
		
		JLabel conteoNo = new JLabel("Conteo: ");
		conteoNo.setBounds(290, 75, 90, 25);
		add(conteoNo);
		
		numConteoSi = new JTextArea();//cantidad de Si
		numConteoSi.setEditable(false);
		numConteoSi.setBounds(90,79,100,25);
		add(numConteoSi);
		
		numConteoNo = new JTextArea();//Cantidad de No
		numConteoNo.setEditable(false);
		numConteoNo.setBounds(340,79,100,25);
		add(numConteoNo);
		
		indicesSi = new JTextArea();//area de texto indices Si
		indicesSi.setEditable(false);
		indicesSi.setLineWrap(true);
		indicesSi.setWrapStyleWord(true);
	    JScrollPane scrollSi = new JScrollPane(indicesSi);
	    scrollSi.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollSi.setBounds(42,120,210,320);
	    add(scrollSi);
	    
	    indicesNo = new JTextArea();//area de texto indides No
		indicesNo.setEditable(false);
		indicesNo.setLineWrap(true);
		indicesNo.setWrapStyleWord(true);
	    JScrollPane scrollNo = new JScrollPane(indicesNo);
	    scrollNo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollNo.setBounds(290,120,210,320);
	    add(scrollNo);
	    
	    pgsBar = new JProgressBar();//Barra de progreso
	    pgsBar.setBounds(520, 20, 245, 55);
	    add(pgsBar);
	    pgsBar.setValue(0);
	    pgsBar.setStringPainted(true);
	    
	    siBar = new JProgressBar();//Histograma Si
	    siBar.setBounds(520, 170, 245, 40);
	    add(siBar);
	    siBar.setValue(0);
	    siBar.setStringPainted(true);
	    
	    noBar = new JProgressBar();//Histograma No
	    noBar.setBounds(520, 210, 245, 40);
	    add(noBar);
	    noBar.setValue(0);
	    noBar.setStringPainted(true);
	    
	    JLabel porcentajeSi = new JLabel("Porcentaje Si:");
	    porcentajeSi.setBounds(530, 100, 150, 25);
	    add(porcentajeSi);
	    
	    JLabel porcentajeNo = new JLabel("Porcentaje No:");
	    porcentajeNo.setBounds(530, 135, 150, 25);
	    add(porcentajeNo);
	    
	    numPorcentajeSi = new JTextArea();//porcentaje Si en pantalla
	    numPorcentajeSi.setEditable(false);
	    numPorcentajeSi.setBounds(640,100,100,25);
		add(numPorcentajeSi);
		
		numPorcentajeNo = new JTextArea();//porcentaje No en pantalla
	    numPorcentajeNo.setEditable(false);
	    numPorcentajeNo.setBounds(640,135,100,25);
		add(numPorcentajeNo);
		
		start = new JButton("Iniciar");
		start.setBounds(580,260,145,40);
		start.addActionListener(this);
		add(start);
	    
	}//Constructor
	
	class MostrarDatos extends Thread{
		ArrayList<String> dts = new ArrayList<String>();
		
		public MostrarDatos(ArrayList<String> dts) {
			this.dts = dts;
		}
		
		public void run() {
			int sz = dts.size();
			int szc = sz/100;
			String x="",y="";//Son cadenas que después van a las cajas de texto
			for (int i = 0; i <sz; i++) {
				if (dts.get(i)=="Si") {//Decide a que cadena asignar el indice
					x+=(i+"\n");
				}else {
					y+=(i+"\n");
				}
				if ((i+1)%szc==0) {//Pasa los indices a las cajas de texto solo cuando son n/100 elementos
					indicesSi.append(x);
					indicesNo.append(y);
					x="";//limpia las cadenas
					y="";
				}
			}
			
		}
		
	}//class MostrarDatos
	
	class Histograma extends Thread{
		
		public void run() {
			int sz = datos.size();
			int szc = sz/100;
			int i = 0;
			NumberFormat df = NumberFormat.getPercentInstance();
			df.setMinimumFractionDigits(2);
			
			while(i<100) {
				int iSi = indicesSi.getLineCount();//consigue la cantidad de lineas de las cajas de texto
				int iNo = indicesNo.getLineCount();
				if ((iSi+iNo)>=((i+1)*szc)) {//verifica si se introdujeron más lineas
					i+=1;
					
					numConteoSi.setText(""+(iSi-1));//cambia el conteo
					numConteoNo.setText(""+(iNo-1));
					
					double pSi = (double)iSi/((double)iSi+(double)iNo);//cambia porcentajes de Si y No
					double pNo = 1-pSi;
					numPorcentajeSi.setText(df.format(pSi));
					numPorcentajeNo.setText(df.format(pNo));
					
					pgsBar.setValue(i);//cambia los progress bar
					siBar.setValue((int)(pSi*100));
					noBar.setValue((int)(pNo*100));
					
				}
				try {
					currentThread().sleep(1000);//un delay para no hacer uso excesivo del núcleo
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}//class MostrarDatos

	@Override
	public void actionPerformed(ActionEvent e) {//ejecuta MostrarDatos e Histograma
		if (e.getSource()==start) {
			
			Runtime runtime = Runtime.getRuntime();
			int ap = runtime.availableProcessors()-1;
			
			while(datos.size()%ap!=0) {
				ap-=1;
			}
			for (int i = 0; i < ap; i++) {
				ArrayList<String> dts;
				dts = new ArrayList<String>(datos.subList((i)*(datos.size()/ap), ((i+1))*(datos.size()/ap)));
				
				MostrarDatos md = new MostrarDatos(dts);
				md.start();	
			}
			Histograma hg = new Histograma();
			hg.start();
		}
		
	}//actionPerformed

}// class Concurrencia


public class PruebaConcurrencia {
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Concurrencia();
			}
		});
		
	}
	
}

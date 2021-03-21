import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

class Concurrencia extends JFrame{
	
	public Concurrencia(){
		
		getContentPane().setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Concurrencia");
		setSize(800,500);
		setLocationRelativeTo(null);
		setVisible(true);
		
		ArrayList<String> datos = new ArrayList<String>();//generación datos
		for (int i = 0; i < 10000000; i++) {
			int x = (int)(Math.round(Math.random()));
			if (x==0) {
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
		
		JTextArea numConteoSi = new JTextArea();
		numConteoSi.setEditable(false);
		numConteoSi.setBounds(90,79,100,25);
		add(numConteoSi);
		
		JTextArea numConteoNo = new JTextArea();
		numConteoNo.setEditable(false);
		numConteoNo.setBounds(340,79,100,25);
		add(numConteoNo);
		
		JTextArea indicesSi = new JTextArea();
		indicesSi.setEditable(false);
		indicesSi.setLineWrap(true);
		indicesSi.setWrapStyleWord(true);
	    JScrollPane scrollSi = new JScrollPane(indicesSi);
	    scrollSi.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollSi.setBounds(42,120,210,320);
	    add(scrollSi);
	    
	    JTextArea indicesNo = new JTextArea();
		indicesNo.setEditable(false);
		indicesNo.setLineWrap(true);
		indicesNo.setWrapStyleWord(true);
	    JScrollPane scrollNo = new JScrollPane(indicesNo);
	    scrollNo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollNo.setBounds(290,120,210,320);
	    add(scrollNo);
	    
	    
	    
	}
	


}


public class PruebaConcurrencia {
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Concurrencia();
			}
		});
		
	}
	
}

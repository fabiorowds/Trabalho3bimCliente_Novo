package br.univel.visao;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JScrollPane;

import java.awt.Insets;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.univel.model.Curso;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaCurso extends JFrame {

	private JPanel contentPane;
	private JTextField txtCurso;
	private JTable tblCursos;
	private List<Curso> cursos;
	private ModeloTabelaCurso mtc;
	private static final String END_WEBSERVICE = "http://trabalho3bim-fabiorowds.rhcloud.com/Trabalho3bim/rest/cursos";
	private Long id;
	private JButton btnApagar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCurso frame = new TelaCurso();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaCurso() {
		id = new Long(0);
		cursos = new ArrayList<Curso>();
		mtc = new ModeloTabelaCurso(cursos);
		try {
			recuperarCursosWS();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setTitle("Cadastro de cursos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 625, 421);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblCurso = new JLabel("Curso:");
		GridBagConstraints gbc_lblCurso = new GridBagConstraints();
		gbc_lblCurso.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurso.anchor = GridBagConstraints.EAST;
		gbc_lblCurso.gridx = 0;
		gbc_lblCurso.gridy = 0;
		panel.add(lblCurso, gbc_lblCurso);
		
		txtCurso = new JTextField();
		GridBagConstraints gbc_txtCurso = new GridBagConstraints();
		gbc_txtCurso.gridwidth = 3;
		gbc_txtCurso.insets = new Insets(0, 0, 5, 0);
		gbc_txtCurso.anchor = GridBagConstraints.NORTH;
		gbc_txtCurso.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCurso.gridx = 1;
		gbc_txtCurso.gridy = 0;
		panel.add(txtCurso, gbc_txtCurso);
		txtCurso.setColumns(10);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtCurso.getText().isEmpty()){
					Curso c = new Curso();
					c.setNome(txtCurso.getText());
					if (!(id.intValue() == 0))
						c.setId(id);
					id = new Long(0);
					try {
						gravarCursosWS(c);
						txtCurso.setText("");
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage(), "Erro ao gravar", JOptionPane.ERROR_MESSAGE);
					}
					try {
						recuperarCursosWS();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Campo de nome do curso não preenchido!", "Dados incompletos", JOptionPane.ERROR_MESSAGE);
				}
				btnApagar.setEnabled(false);
			}
		});
		GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
		gbc_btnConfirmar.insets = new Insets(0, 0, 0, 5);
		gbc_btnConfirmar.gridx = 1;
		gbc_btnConfirmar.gridy = 1;
		panel.add(btnConfirmar, gbc_btnConfirmar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtCurso.setText("");
				btnApagar.setEnabled(false);
				id = new Long(0);
			}
		});
		
		btnApagar = new JButton("Apagar");
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (id.intValue() > 0) {
					if (JOptionPane.showConfirmDialog(null, "Deseja deletar o curso selecionado?", "Confirmar operação!", JOptionPane.YES_NO_OPTION) == 
							JOptionPane.NO_OPTION){
						return;
					}
					try {
						apagarCursosWS(id.intValue());
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro ao excluir Curso", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
					id = new Long(0);
					txtCurso.setText("");
					btnApagar.setEnabled(false);
					try {
						recuperarCursosWS();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnApagar.setEnabled(false);
		GridBagConstraints gbc_btnApagar = new GridBagConstraints();
		gbc_btnApagar.insets = new Insets(0, 0, 0, 5);
		gbc_btnApagar.gridx = 2;
		gbc_btnApagar.gridy = 1;
		panel.add(btnApagar, gbc_btnApagar);
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.gridx = 3;
		gbc_btnCancelar.gridy = 1;
		panel.add(btnCancelar, gbc_btnCancelar);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		tblCursos = new JTable(mtc);
		tblCursos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2){
					if (tblCursos.getRowCount() > 0) {
						Curso c = cursos.get(tblCursos.getSelectedRow());
						txtCurso.setText(c.getNome());
						id = c.getId();
						btnApagar.setEnabled(true);
					}
				}
			}
		});
		scrollPane.setViewportView(tblCursos);
		
	}
	private void recuperarCursosWS() throws Exception{
		cursos.clear();
		ClientRequest request = new ClientRequest(END_WEBSERVICE);
		request.accept("application/json");
		ClientResponse<String> response = request.get(String.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			new ByteArrayInputStream(response.getEntity().getBytes())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			ObjectMapper mapper = new ObjectMapper();
			Curso[] cs = mapper.readValue(output, Curso[].class);
			for (Curso c : cs){
				cursos.add(c);
			}
			System.out.println(output+"\n");
		}
		mtc.fireTableDataChanged();
	}
	
	private void gravarCursosWS(Curso c) throws Exception {
		ClientRequest request = new ClientRequest(END_WEBSERVICE);
		if (c.getId() == null){
			request = new ClientRequest(END_WEBSERVICE);
		} else {
			request = new ClientRequest(END_WEBSERVICE + "/" + c.getId().toString());
		} 
		request.accept("application/json");
		ObjectMapper mapper = new ObjectMapper();
		String retorno = mapper.writeValueAsString(c);
		request.body("application/json", retorno);
		ClientResponse<String> response;
		if (c.getId() == null){
			response = request.post(String.class);
		} else {
			response = request.put(String.class);
		}
		if ((response.getStatus() != 201) && (response.getStatus() != 204)) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
		}
	}
	
	private void apagarCursosWS(int id) throws Exception{
		ClientRequest request = new ClientRequest(END_WEBSERVICE + "/" + id);
		request.accept("application/json");
		ClientResponse<String> response = request.delete(String.class);
		if ((response.getStatus() != 200) && (response.getStatus() != 204)) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
		}
	}

}

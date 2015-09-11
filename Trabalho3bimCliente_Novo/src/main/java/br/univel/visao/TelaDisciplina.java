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
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.univel.model.Curso;
import br.univel.model.Disciplina;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaDisciplina extends JFrame {

	private JPanel contentPane;
	private JTextField txtDisciplina;
	private JTable tblDisciplinas;
	private List<Disciplina> disciplinas;
	private JTextField txtCurso;
	private static final String END_WEBSERVICE = "http://trabalho3bim-fabiorowds.rhcloud.com/Trabalho3bim/rest/disciplinas";
	private ModeloTabelaDisciplina mtd;
	private JButton btnApagar;
	private Long id;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaDisciplina frame = new TelaDisciplina();
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
	public TelaDisciplina() {
		id = new Long(0);
		setTitle("Cadastro de Disciplinas");
		disciplinas = new ArrayList<Disciplina>();
		mtd = new ModeloTabelaDisciplina(disciplinas);
		try {
			recuperarDisciplinasWS();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
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
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblDisciplina = new JLabel("Disciplina:");
		GridBagConstraints gbc_lblDisciplina = new GridBagConstraints();
		gbc_lblDisciplina.anchor = GridBagConstraints.EAST;
		gbc_lblDisciplina.insets = new Insets(0, 0, 5, 5);
		gbc_lblDisciplina.gridx = 0;
		gbc_lblDisciplina.gridy = 0;
		panel.add(lblDisciplina, gbc_lblDisciplina);
		
		txtDisciplina = new JTextField();
		GridBagConstraints gbc_txtDisciplina = new GridBagConstraints();
		gbc_txtDisciplina.gridwidth = 3;
		gbc_txtDisciplina.insets = new Insets(0, 0, 5, 0);
		gbc_txtDisciplina.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDisciplina.gridx = 1;
		gbc_txtDisciplina.gridy = 0;
		panel.add(txtDisciplina, gbc_txtDisciplina);
		txtDisciplina.setColumns(10);
		
		JLabel lblCurso = new JLabel("Curso:");
		GridBagConstraints gbc_lblCurso = new GridBagConstraints();
		gbc_lblCurso.anchor = GridBagConstraints.EAST;
		gbc_lblCurso.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurso.gridx = 0;
		gbc_lblCurso.gridy = 1;
		panel.add(lblCurso, gbc_lblCurso);
		
		txtCurso = new JTextField();
		GridBagConstraints gbc_txtCurso = new GridBagConstraints();
		gbc_txtCurso.gridwidth = 3;
		gbc_txtCurso.insets = new Insets(0, 0, 5, 0);
		gbc_txtCurso.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCurso.gridx = 1;
		gbc_txtCurso.gridy = 1;
		panel.add(txtCurso, gbc_txtCurso);
		txtCurso.setColumns(10);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtDisciplina.getText().isEmpty() && !txtCurso.getText().isEmpty()) {
					Disciplina d = new Disciplina();
					d.setNome(txtDisciplina.getText());
					d.setCurso(txtCurso.getText());
					if (!(id.intValue() == 0)) {
						d.setId(id);
						id = new Long(0);
					}
					try {
						gravarDisciplinaWS(d);
						txtCurso.setText("");
						txtDisciplina.setText("");
						JOptionPane.showMessageDialog(null, "Sucesso ao gravar disciplina", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
						
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro ao gravar", JOptionPane.ERROR_MESSAGE);						
						e1.printStackTrace();
					}
					try {
						recuperarDisciplinasWS();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					btnApagar.setEnabled(false);
				} else {
					JOptionPane.showMessageDialog(null, "Campo de nome do curso e/ou nome da disciplina não preenchidos!", "Dados incompletos", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
		gbc_btnConfirmar.insets = new Insets(0, 0, 0, 5);
		gbc_btnConfirmar.gridx = 1;
		gbc_btnConfirmar.gridy = 2;
		panel.add(btnConfirmar, gbc_btnConfirmar);
		
		btnApagar = new JButton("Apagar");
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (id.intValue() > 0) {
					if (JOptionPane.showConfirmDialog(null, "Deseja deletar a Disciplina selecionada?", "Confirmar operação!", JOptionPane.YES_NO_OPTION) == 
							JOptionPane.NO_OPTION){
						return;
					}
					try {
						apagarDisciplinaWS(id.intValue());
						id = new Long(0);
						txtCurso.setText("");
						txtDisciplina.setText("");
						btnApagar.setEnabled(false);
						try {
							recuperarDisciplinasWS();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro ao excluir Disciplina", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}
			}
		});
		btnApagar.setEnabled(false);
		GridBagConstraints gbc_btnApagar = new GridBagConstraints();
		gbc_btnApagar.insets = new Insets(0, 0, 0, 5);
		gbc_btnApagar.gridx = 2;
		gbc_btnApagar.gridy = 2;
		panel.add(btnApagar, gbc_btnApagar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtCurso.setText("");
				txtDisciplina.setText("");
				btnApagar.setEnabled(false);
				id = new Long(0);
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.gridx = 3;
		gbc_btnCancelar.gridy = 2;
		panel.add(btnCancelar, gbc_btnCancelar);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		tblDisciplinas = new JTable(mtd);
		tblDisciplinas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2){
					if (tblDisciplinas.getRowCount() > 0){
						Disciplina d = disciplinas.get(tblDisciplinas.getSelectedRow());
						id = d.getId();
						txtDisciplina.setText(d.getNome());
						txtCurso.setText(d.getCurso());
						btnApagar.setEnabled(true);
					}
				}
			}
		});
		scrollPane.setViewportView(tblDisciplinas);
	}
	
	private void recuperarDisciplinasWS() throws Exception{
		disciplinas.clear();
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
			Disciplina[] discip = mapper.readValue(output, Disciplina[].class);
			for (Disciplina d : discip) {
				disciplinas.add(d);
			}
			System.out.println(output + "\n");
		}
		mtd.fireTableDataChanged();
	}
	private void gravarDisciplinaWS(Disciplina d) throws Exception {
		ClientRequest request = new ClientRequest(END_WEBSERVICE);
		if (d.getId() == null){
			request = new ClientRequest(END_WEBSERVICE);
		} else {
			request = new ClientRequest(END_WEBSERVICE + "/" + d.getId().toString());
		} 
		request.accept("application/json");
		ObjectMapper mapper = new ObjectMapper();
		String retorno = mapper.writeValueAsString(d);
		request.body("application/json", retorno);
		ClientResponse<String> response;
		if (d.getId() == null){
			response = request.post(String.class);
		} else {
			response = request.put(String.class);
		}
		if ((response.getStatus() != 201) && (response.getStatus() != 204)) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
		}
	}
	private void apagarDisciplinaWS(int id) throws Exception{
		ClientRequest request = new ClientRequest(END_WEBSERVICE + "/" + id);
		request.accept("application/json");
		ClientResponse<String> response = request.delete(String.class);
		if ((response.getStatus() != 200) && (response.getStatus() != 204)) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
		}
	}


}

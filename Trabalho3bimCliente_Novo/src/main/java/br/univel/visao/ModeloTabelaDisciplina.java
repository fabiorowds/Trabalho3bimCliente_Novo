package br.univel.visao;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.univel.model.Disciplina;

public class ModeloTabelaDisciplina extends AbstractTableModel{
    //Alterado para commit
	List<Disciplina> disciplinas = new ArrayList<Disciplina>();
	
	public ModeloTabelaDisciplina(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
	public int getRowCount() {
		return disciplinas.size();
	}

	public int getColumnCount() {
		return 3;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return disciplinas.get(rowIndex).getId();
		case 1:
			return disciplinas.get(rowIndex).getNome();
		case 2:
			return disciplinas.get(rowIndex).getCurso();
		default:
			break;
		}
		return null;
	}
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "ID";
		case 1:
			return "Nome";
		case 2:
			return "Curso";
		default:
			break;
		}
		return super.getColumnName(column);
	}

	
	
	
}

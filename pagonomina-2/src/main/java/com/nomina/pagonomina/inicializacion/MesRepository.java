package com.nomina.pagonomina.inicializacion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.nomina.pagonomina.modelo.Mes;
import com.nomina.pagonomina.repository.IMesRepository;

@Repository
@Scope("application")
public class MesRepository implements CommandLineRunner, IMesRepository {
	private List<Mes> listaMeses;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Mes enero = new Mes(0, "enero", 31, null, 1, 7, 8, 9, 14,15, 21, 22, 28, 29);
		Mes febrero = new Mes(1, "febrero", 28, enero, 4, 5, 11, 12, 18, 19, 25, 26);
		Mes marzo = new Mes(2, "marzo", 31, febrero, 4, 5, 11, 12, 18, 19, 20, 25, 26);
		Mes abril = new Mes(3, "abril", 30, marzo, 1, 2, 6, 7, 8, 9, 15, 16, 22, 23, 29, 30);
		Mes mayo = new Mes(4, "mayo", 31, abril, 1, 6, 7, 13, 14, 20, 21, 22, 27, 28);
		Mes junio = new Mes(5, "junio", 30, mayo, 3, 4, 10, 11, 12, 17, 18, 19, 24, 25);
		Mes julio = new Mes(6, "julio", 31, junio, 1, 2, 3, 8, 9, 15, 16, 20, 22, 23, 29, 30);
		Mes agosto = new Mes(7, "agosto", 31, julio, 5, 6, 7, 12, 13, 19, 20, 21, 26, 27);
		Mes septiembre = new Mes(8, "septiembre", 30, agosto, 2, 3, 9, 10, 16, 17, 23, 24, 30);
		Mes octubre = new Mes(9, "octubre", 31, septiembre, 1, 7, 8, 14, 15, 21, 22, 16, 28, 29);
		Mes noviembre = new Mes(10, "noviembre", 30, octubre, 4, 5, 6, 11, 12, 13, 18, 19, 25, 26);
		Mes diciembre = new Mes(11, "diciembre", 31, noviembre, 2, 3, 8, 9, 10, 16, 17, 23, 24, 25, 30, 31);
		listaMeses = new ArrayList<>();
		listaMeses.add(enero);
		listaMeses.add(febrero);
		listaMeses.add(marzo);
		listaMeses.add(abril);
		listaMeses.add(mayo);
		listaMeses.add(junio);
		listaMeses.add(julio);
		listaMeses.add(agosto);
		listaMeses.add(septiembre);
		listaMeses.add(octubre);
		listaMeses.add(noviembre);
		listaMeses.add(diciembre);
		enero.setProximoMes(febrero);
		febrero.setProximoMes(marzo);
		marzo.setProximoMes(abril);
		abril.setProximoMes(mayo);
		mayo.setProximoMes(junio);
		junio.setProximoMes(julio);
		julio.setProximoMes(agosto);
		agosto.setProximoMes(septiembre);
		septiembre.setProximoMes(octubre);
		octubre.setProximoMes(noviembre);
		noviembre.setProximoMes(diciembre);
		this.setListaMeses(listaMeses);
	}

	public List<Mes> getListaMeses() {
		return listaMeses;
	}

	public void setListaMeses(List<Mes> listaMeses) {
		this.listaMeses = listaMeses;
	}

	@Override
	public List<Mes> encontrarTodos() {
		// TODO Auto-generated method stub
		return getListaMeses();
	}

}

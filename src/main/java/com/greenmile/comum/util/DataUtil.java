package com.greenmile.comum.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Juciel Almeida
 * 
 * @class DataUtil
 * 
 *        Classe utilitária para lidar com instâncias de Date em diferentes
 *        situações
 * 
 */
public class DataUtil {

	public static final String FORMATO_DATA_PADRAO = "dd/MM/yyyy";

	public static final String FORMATO_DATA_PADRAO_US = "yyyy-MM-dd";

	public static final String FORMATO_DATA_MES_ANO = "MM/yyyy";

	public static final String TIMEZONE_BR = "GMT-3";
	public static final String VAZIO = "";


	/**
	 * Retorna a data informada formatada para dd/MM/yyyy
	 * 
	 * @return String
	 */
	public static String getDataFormatada(Date data) {

		if (data == null) {
			return "-";
		}

		SimpleDateFormat dataCadastro =
				new SimpleDateFormat(FORMATO_DATA_PADRAO);
		return dataCadastro.format(data);
	}

	/**
	 * Converte um formato de string yyy-MM-dd em um formato data v�lido.
	 * 
	 * @return Date
	 * @throws ParseException
	 */
	public static Date converterStringData(String data) throws ParseException {
		
		String dataFormatada[] = data.split("/");
		String dataA = dataFormatada[2] +"-"+ dataFormatada[1] +"-"+ dataFormatada[0];
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse(dataA);

		return date;

	}
	
	/**
	 * Retorna um {@link java.sql.Date} a partir do {@link Date} informado.
	 * 
	 * @param data
	 * @return java.sql.Date
	 */
	public static java.sql.Date getSQLDate(Date data) {
		if (data != null) {
			return new java.sql.Date(data.getTime());
		}
		return null;
	}


}
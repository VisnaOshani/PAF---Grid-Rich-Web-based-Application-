package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AppointmentAPI
 */
@WebServlet("/AppointmentAPI")
public class AppointmentAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Appointment appObj = new Appointment();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppointmentAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	

		boolean status= Boolean.parseBoolean(request.getParameter("status"));
		System.out.println(request.getParameter("id"));
		
		String output = appObj.insertAppointment(request.getParameter("id"),
												request.getParameter("hospitalid"),
												request.getParameter("patientid"),
												request.getParameter("date"),
												request.getParameter("time"),
												request.getParameter("description"),
												status);
		
		response.getWriter().write(output);
														
				
		// TODO Auto-generated method stub
//		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		Map paras = getParasMap(request);
		String newTime = paras.get("time").toString().replace("%3A", ":");
		
		String des = paras.get("description").toString();
		String newDescription = des.replace("+", " ");
		
		String status = "";
		
		if (paras.get("status").toString().equals("true"))
			status = "1";
		else
			status = "0";
		
		String output = appObj.updateAppointment(
				paras.get("hospitalid").toString(),
				paras.get("patientid").toString(),
				paras.get("date").toString(),
				newTime,
				newDescription,
				status,
				paras.get("id").toString());
		
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		Map paras = getParasMap(request);
		
		String output = appObj.deleteAppointment(paras.get("id").toString());
		
		response.getWriter().write(output);

	}
	
	// Convert request parameters to a Map 
	private static Map getParasMap(HttpServletRequest request)
	{
		Map<String, String> map = new HashMap<String, String>();
		try
		{ 
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ?
								scanner.useDelimiter("\\A").next() : "";
			scanner.close(); 
	 
			String[] params = queryString.split("&");
			for (String param : params)   { 
					String[] p = param.split("=");
					map.put(p[0], p[1]);
			} 
		}
		catch (Exception e)
		{
			
		}  return map;
	} 

}
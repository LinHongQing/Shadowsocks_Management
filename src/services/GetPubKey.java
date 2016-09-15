package services;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;

import utils.RSAUtil;

/**
 * Servlet implementation class GetPubKey
 */
@WebServlet("/getpubkey.action")
public class GetPubKey extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPubKey() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		KeyPair keyPair = RSAUtil.generateKeyPair(false);
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		request.getSession().setAttribute("privateKey", privateKey);
		String moduls = new String(Hex.encodeHex(publicKey.getModulus().toByteArray()));
		String exponent = new String(Hex.encodeHex(publicKey.getPublicExponent().toByteArray()));
		Map<String, String> key = new HashMap<>();
		key.put("moduls", moduls);
		key.put("exponent", exponent);
		JSONObject json = new JSONObject(key);
		System.out.println(json.toString());
		PrintWriter out = response.getWriter();
		out.print(json.toString());// 返给ajax请求
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

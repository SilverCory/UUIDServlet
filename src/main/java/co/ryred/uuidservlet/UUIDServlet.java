package co.ryred.uuidservlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * @author Cory Redmond
 *         Created by acech_000 on 15/09/2015.
 */
public class UUIDServlet extends HttpServlet
{

	private static final Gson gson = new Gson();
	private static final Gson pretty_gson = new GsonBuilder().setPrettyPrinting().create();
	private final HashMap<String, User> users;

	public UUIDServlet() throws Exception
	{

		UUIDServletConfig.init();

		if ( UUIDServletConfig.dataLocation.exists() ) {

			Type listType = new TypeToken<HashMap<String, User>>() {}.getType();
			this.users = gson.fromJson( new FileReader( UUIDServletConfig.dataLocation ), listType );

		}
		else {

			this.users = new HashMap<String, User>();

			// new User( name, UUID, profile, reason );
			this.users.put( "3f199957f1ad4cf798b84ae9e573c219", new User( "acecheesecr14", "3f199957f1ad4cf798b84ae9e573c219", "https://www.spigotmc.org/members/4411/", "Author of this plugin." ) );
			this.users.put( "c26dbfbab18e46198039b82e91eb3143", new User( "GreyWolf", "c26dbfbab18e46198039b82e91eb3143", "https://www.spigotmc.org/members/4411/", "Author of this plugin." ) );
			this.users.put( "efdb71ae2f8c43c795fcb9fb9f4e65e6", new User( "IPonThee", "efdb71ae2f8c43c795fcb9fb9f4e65e6", "https://www.spigotmc.org/members/28046/", "Provider of services and sex." ) );
			this.users.put( "5639fc16812f4009b63b5dd2aa6f815a", new User( "WaterTrooper", "5639fc16812f4009b63b5dd2aa6f815a", null, "Provider of services and sex. Also motivation." ) );
			this.users.put( "7f5c4613c64b4642a0443c949d547d03", new User( "JadeBig1", "7f5c4613c64b4642a0443c949d547d03", null, "Sister's account." ) );
			this.users.put( "643169cb1bd343e1814f5112d7d05157", new User( "reichman2", "643169cb1bd343e1814f5112d7d05157", null, "Long term penis and helperer." ) );
			this.users.put( "ca817560e87c454ebb2ddd6b49708d97", new User( "Empire92", "ca817560e87c454ebb2ddd6b49708d97", "https://www.spigotmc.org/members/13090/", "A beautiful helper and the developer of PlotSquared." ) );
			this.users.put( "61b768d7974d45a3b60f483e3755ef1e", new User( "Log1x", "61b768d7974d45a3b60f483e3755ef1e", "https://www.spigotmc.org/members/35293/", "Great help in finding bugs and amazing person!" ) );
			this.users.put( "b218f96b559e46fe88224bd585dc6b04", new User( "McLive", "b218f96b559e46fe88224bd585dc6b04", "https://www.spigotmc.org/members/1332/", "Amazing python developer and bringer of cravatar." ) );
			this.users.put( "b2d0762b02ba47afb3d1569fa5c3e2d0", new User( "MeMyself", "b2d0762b02ba47afb3d1569fa5c3e2d0", "https://www.spigotmc.org/members/14650/", "This guy is bae. So helpful and kind." ) );
			this.users.put( "4b3479074b454a4bb07587259780f5bf", new User( "acharige", "4b3479074b454a4bb07587259780f5bf", "https://www.spigotmc.org/members/27630/", "Sexy mofo, just an all around great guy!" ) );
			this.users.put( "5dcee3cc526d4a57a198731e304902c3", new User( "nfell2009", "5dcee3cc526d4a57a198731e304902c3", "https://www.spigotmc.org/members/983/", "Ew. Nfell smells." ) );
			this.users.put( "b218f96b559e46fe88224bd585dc6b04", new User( "evensafe789", "b218f96b559e46fe88224bd585dc6b04", "https://www.spigotmc.org/members/21074/", "You aren't evensafe! ;)" ) );
			this.users.put( "13b248bd87f34fc2bcaa0d77218dc8dc", new User( "CougarHD", "13b248bd87f34fc2bcaa0d77218dc8dc", null, "Mega ultimate youtuber!" ) );
			this.users.put( "c9333718537444eebe0a6da927ad18f3", new User( "Chillz_", "c9333718537444eebe0a6da927ad18f3", "https://www.spigotmc.org/members/23378/", "Chilly and not too far from me." ) );

			BufferedWriter bw = new BufferedWriter( new FileWriter( UUIDServletConfig.dataLocation ) );
			bw.write( pretty_gson.toJson( this.users ) );
			bw.flush();
			bw.close();

		}

	}


	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		response.sendRedirect( "https://ryred.co/" );
	}

	@Override
	protected void doTrace( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		resp.sendRedirect( "https://ryred.co/" );
	}

	@Override
	protected void doDelete( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		resp.sendRedirect( "https://ryred.co/" );
	}

	@Override
	protected void doOptions( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		resp.sendRedirect( "https://ryred.co/" );
	}

	protected void doGet( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException
	{

		response.setContentType( "application/json" );

		String[] requestArr = request.getRequestURI().substring( 1 ).replace( "/", "\u5584" ).split( "\u5584" );
		String command = requestArr[ 0 ];

		if ( command.equalsIgnoreCase( "add" ) ) {

			String key = request.getParameter( "key" );
			String uuid = request.getParameter( "uuid" );
			String name = request.getParameter( "name" );
			String reason = request.getParameter( "reason" );
			String profile = request.getParameter( "profile" );

			if ( key == null || !key.equals( UUIDServletConfig.password ) ) {
				response.setStatus( 401 );
				response.getOutputStream().print( "\"error\": \"You are unauthorised to be here.\" }" );
				return;
			}

			if ( uuid == null || name == null ) {
				response.setStatus( 400 );
				response.getOutputStream().print( "\"error\": \"Name and UUID are not set. You may wish to have the reason and profile aswell.\" }" );
				return;
			}

			uuid = uuid.replace( "-", "" );
			this.users.put( uuid, new User( name, uuid, profile, reason ) );

			BufferedWriter bw = new BufferedWriter( new FileWriter( UUIDServletConfig.dataLocation ) );
			bw.write( pretty_gson.toJson( this.users ) );
			bw.flush();
			bw.close();

			return;

		}

		response.setStatus( 200 );
		if ( request.getParameter( "min" ) != null ) {
			response.getOutputStream().print( gson.toJson( this.users ) );
		}
		else {
			response.getOutputStream().print( "#Well, this is all my baes and people that deserve recognition as well as me.\n" );
			response.getOutputStream().print( pretty_gson.toJson( this.users ) );
		}

	}

}

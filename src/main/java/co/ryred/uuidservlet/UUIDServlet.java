package co.ryred.uuidservlet;

import co.ryred.uuidservlet.user.User;
import co.ryred.uuidservlet.user.UserSerialisation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Cory Redmond
 *         Created by acech_000 on 15/09/2015.
 */
public class UUIDServlet extends HttpServlet
{

	private static final Gson full_gson = new Gson();
	private static final Gson gson = new GsonBuilder().registerTypeAdapter( User.class, new UserSerialisation() ).create();
	private static final Gson pretty_gson = new GsonBuilder().registerTypeAdapter( User.class, new UserSerialisation() ).setPrettyPrinting().create();
	private static final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
	private static final ConcurrentHashMap<String, Status> builds = new ConcurrentHashMap<>();

	private static boolean savingUsers = false;
	private static boolean savingBuilds = false;

	public UUIDServlet() throws Exception
	{

		UUIDServletConfig.init();

		if ( UUIDServletConfig.dataLocation.exists() ) {

			Type listType = new TypeToken<HashMap<String, User>>() {}.getType();
			users.putAll( gson.fromJson( new FileReader( UUIDServletConfig.dataLocation ), listType ) );

		}
		else {

			// new User( name, UUID, profile, reason );
			users.put( "3f199957f1ad4cf798b84ae9e573c219", new User( "acecheesecr14", "3f199957f1ad4cf798b84ae9e573c219", "https://www.spigotmc.org/members/4411/", "Author of this plugin." ) );
			users.put( "c26dbfbab18e46198039b82e91eb3143", new User( "GreyWolf", "c26dbfbab18e46198039b82e91eb3143", "https://www.spigotmc.org/members/4411/", "Author of this plugin." ) );
			users.put( "efdb71ae2f8c43c795fcb9fb9f4e65e6", new User( "IPonThee", "efdb71ae2f8c43c795fcb9fb9f4e65e6", "https://www.spigotmc.org/members/28046/", "Provider of services and sex." ) );
			users.put( "5639fc16812f4009b63b5dd2aa6f815a", new User( "WaterTrooper", "5639fc16812f4009b63b5dd2aa6f815a", null, "Provider of services and sex. Also motivation." ) );
			users.put( "7f5c4613c64b4642a0443c949d547d03", new User( "JadeBig1", "7f5c4613c64b4642a0443c949d547d03", null, "Sister's account." ) );
			users.put( "643169cb1bd343e1814f5112d7d05157", new User( "reichman2", "643169cb1bd343e1814f5112d7d05157", null, "Long term penis and helperer." ) );
			users.put( "61b768d7974d45a3b60f483e3755ef1e", new User( "Log1x", "61b768d7974d45a3b60f483e3755ef1e", "https://www.spigotmc.org/members/35293/", "Great help in finding bugs and amazing person!" ) );
			users.put( "b218f96b559e46fe88224bd585dc6b04", new User( "McLive", "b218f96b559e46fe88224bd585dc6b04", "https://www.spigotmc.org/members/1332/", "Amazing python developer and bringer of cravatar." ) );
			users.put( "b2d0762b02ba47afb3d1569fa5c3e2d0", new User( "MeMyself", "b2d0762b02ba47afb3d1569fa5c3e2d0", "https://www.spigotmc.org/members/14650/", "This guy is bae. So helpful and kind." ) );
			users.put( "4b3479074b454a4bb07587259780f5bf", new User( "acharige", "4b3479074b454a4bb07587259780f5bf", "https://www.spigotmc.org/members/27630/", "Sexy mofo, just an all around great guy!" ) );
			users.put( "5dcee3cc526d4a57a198731e304902c3", new User( "nfell2009", "5dcee3cc526d4a57a198731e304902c3", "https://www.spigotmc.org/members/983/", "Ew. Nfell smells." ) );
			users.put( "b218f96b559e46fe88224bd585dc6b04", new User( "evensafe789", "b218f96b559e46fe88224bd585dc6b04", "https://www.spigotmc.org/members/21074/", "You aren't evensafe! ;)" ) );
			users.put( "13b248bd87f34fc2bcaa0d77218dc8dc", new User( "CougarHD", "13b248bd87f34fc2bcaa0d77218dc8dc", null, "Mega ultimate youtuber!" ) );
			users.put( "c9333718537444eebe0a6da927ad18f3", new User( "Chillz_", "c9333718537444eebe0a6da927ad18f3", "https://www.spigotmc.org/members/23378/", "Chilly and not too far from me." ) );

			BufferedWriter bw = new BufferedWriter( new FileWriter( UUIDServletConfig.dataLocation ) );
			bw.write( pretty_gson.toJson( users ) );
			bw.flush();
			bw.close();

		}

		if ( UUIDServletConfig.statusConfigFile.exists() ) {
			Type listType = new TypeToken<HashMap<String, Status>>() {}.getType();
			builds.putAll( gson.fromJson( new FileReader( UUIDServletConfig.statusConfigFile ), listType ) );
		}
		else {
			builds.put( UUID.randomUUID().toString(), Status.EH );
			saveBuilds();
		}

	}

	private void saveBuilds()
	{

		if ( savingBuilds ) return;
		savingBuilds = true;

		try {
			new Thread( "BuildSaveThread" )
			{
				@Override
				public void run()
				{
					try {

						savingBuilds = true;

						BufferedWriter bw = new BufferedWriter( new FileWriter( UUIDServletConfig.statusConfigFile ) );
						bw.write( pretty_gson.toJson( UUIDServlet.builds ) );
						bw.flush();
						bw.close();

					} catch ( Exception e ) {
						e.printStackTrace();
						savingBuilds = false;
					}
				}
			}.start();
		} catch ( Exception e ) {
			e.printStackTrace();
			savingBuilds = false;
		}
	}

	private void saveUsers()
	{

		if ( savingUsers ) return;
		savingUsers = true;

		try {
			new Thread( "UserSaveThread" )
			{
				@Override
				public void run()
				{
					try {

						savingUsers = true;

						BufferedWriter bw = new BufferedWriter( new FileWriter( UUIDServletConfig.dataLocation ) );
						bw.write( pretty_gson.toJson( UUIDServlet.users ) );
						bw.flush();
						bw.close();

					} catch ( Exception e ) {
						e.printStackTrace();
						savingUsers = false;
					}
				}
			}.start();
		} catch ( Exception e ) {
			e.printStackTrace();
			savingUsers = false;
		}
	}

	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{

		response.setContentType( "application/json" );

		String[] requestArr = request.getRequestURI().substring( 1 ).replace( "/", "\u5584" ).split( "\u5584" );
		String command = "";

		if ( requestArr.length >= 1 ) { command = requestArr[ 0 ]; }

		if ( command.equalsIgnoreCase( "add" ) ) {

			String key = request.getParameter( "key" );
			String uuid = request.getParameter( "uuid" );
			String name = request.getParameter( "name" );
			String reason = request.getParameter( "reason" );
			String profile = request.getParameter( "profile" );
			String html = request.getParameter( "html" );

			if ( key == null || !key.equals( UUIDServletConfig.password ) ) {
				response.setStatus( 401 );
				response.getOutputStream().print( "{\"error\": \"You are unauthorised to be here.\" }" );
				return;
			}

			if ( uuid == null || name == null ) {
				response.setStatus( 400 );
				response.getOutputStream().print( "{\"error\": \"Name and UUID are not set. You may wish to have the reason and profile aswell.\" }" );
				return;
			}

			uuid = uuid.replace( "-", "" );
			users.put( uuid, new User( name, uuid, profile, reason, html ) );
			saveUsers();
			response.getOutputStream().print( "{\"success\": \"Done.\" }" );

		}
		else {
			response.sendRedirect( "https://ryred.co/" );
		}

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
		String command = "";

		if ( requestArr.length >= 1 ) { command = requestArr[ 0 ]; }

		if ( command.equalsIgnoreCase( "add" ) ) {

			String key = request.getParameter( "key" );
			String uuid = request.getParameter( "uuid" );
			String name = request.getParameter( "name" );
			String reason = request.getParameter( "reason" );
			String profile = request.getParameter( "profile" );

			if ( key == null || !key.equals( UUIDServletConfig.password ) ) {
				response.setStatus( 401 );
				response.getOutputStream().print( "{\"error\": \"You are unauthorised to be here.\" }" );
				return;
			}

			if ( uuid == null || name == null ) {
				response.setStatus( 400 );
				response.getOutputStream().print( "{\"error\": \"Name and UUID are not set. You may wish to have the reason and profile aswell.\" }" );
				return;
			}

			uuid = uuid.replace( "-", "" );
			users.put( uuid, new User( name, uuid, profile, reason, null ) );
			saveUsers();
			response.getOutputStream().print( "{\"success\": \"Done.\" }" );
			return;

		}
		else if ( command.equalsIgnoreCase( "status" ) ) {

			if ( requestArr.length >= 2 ) {

				String buildName = requestArr[ 1 ];

				if ( requestArr.length >= 3 && "set".equalsIgnoreCase( requestArr[ 2 ] ) ) {
					String key = request.getParameter( "key" );
					String statusStr = request.getParameter( "status" );

					Status status;

					try {
						int statusNumber = Integer.valueOf( statusStr );
						switch ( statusNumber ) {
							case 0:
								status = Status.GOOD;
								break;
							case 1:
								status = Status.EH;
								break;
							case 2:
							default:
								status = Status.BAD;
								break;
						}
					} catch ( Exception e ) {
						status = Status.BAD;
					}

					if ( key == null || !key.equals( UUIDServletConfig.password ) ) {
						response.setStatus( 401 );
						response.getOutputStream().print( "{\"error\": \"You are unauthorised to be here.\" }" );
						return;
					}

					if ( buildName == null ) {
						response.setStatus( 400 );
						response.getOutputStream().print( "{\"error\": \"Invalid buildname.\" }" );
						return;
					}

					builds.put( buildName.toUpperCase(), status );
					saveBuilds();

					response.getOutputStream().print( "{\"success\": \"Done.\" }" );
					return;
				}

				if ( buildName == null ) buildName = "";
				buildName = buildName.toUpperCase();

				response.setContentType( "image/png" );
				Status status = builds.get( buildName );
				if ( status == null ) status = Status.BAD;

				try {

					File file;

					switch ( status ) {

						case GOOD:
							file = UUIDServletConfig.goodFile;
							break;

						case EH:
							file = UUIDServletConfig.unstableFile;
							break;

						case BAD:
						default:
							file = UUIDServletConfig.badFile;
							break;

					}

					ImageIO.write( ImageIO.read( file ), "png", response.getOutputStream() );

				} catch ( Exception e ) {
					try {
						ImageIO.write( ImageIO.read( UUIDServletConfig.badFile ), "png", response.getOutputStream() );
					} catch ( Exception ex ) {
						response.setContentType( "application/json" );
						response.getOutputStream().print( "{\"error\": \"Invalid buildname.\\n" + e.getMessage() + "\\n" + e.getMessage() + "\" }" );
					}
				}

				return;

			}

		}

		response.setStatus( 200 );

		if ( request.getParameter( "min" ) != null ) {
			response.getOutputStream().print( gson.toJson( users ) );
		}
		else if ( request.getParameter( "full" ) != null ) {
			response.getOutputStream().print( full_gson.toJson( users ) );
		}
		else {
			response.getOutputStream().print( "#Well, this is all my baes and people that deserve recognition as well as me.\n" );
			response.getOutputStream().print( pretty_gson.toJson( users ) );
		}

	}

}

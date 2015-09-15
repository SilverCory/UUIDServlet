package co.ryred.uuidservlet;

import com.google.common.base.Throwables;
import co.ryred.uuidservlet.configuration.InvalidConfigurationException;
import co.ryred.uuidservlet.configuration.file.YamlConfiguration;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * @author Cory Redmond
 *         Created by acech_000 on 03/04/2015.
 */
public class UUIDServletConfig
{


	private static final File CONFIG_FILE = new File( new File( System.getProperty( "catalina.base" ), "conf" ), "UUIDServlet.yml" );
	private static final String HEADER = "YAML only please!";
	public static YamlConfiguration config;
	public static boolean debug;
	public static File dataLocation;
	public static String password;
	static int version;
	private static boolean initted = false;

	public static void init()
	{
		init( false );
	}

	public static void init( boolean force )
	{
		if ( !force && initted ) {
			Logger.getRootLogger().log( Level.WARN, "Config was already init'd." );
			return;
		}

		if ( !CONFIG_FILE.exists() ) {
			CONFIG_FILE.getParentFile().mkdirs();
		}

		config = new YamlConfiguration();
		try {
			Logger.getRootLogger().log( Level.INFO, "Config dir: " + CONFIG_FILE.getAbsolutePath() );
			config.load( CONFIG_FILE );
		} catch ( IOException ex ) {
		} catch ( InvalidConfigurationException ex ) {
			Logger.getRootLogger().log( Level.FATAL, "Could not load UUIDServlet.yml, please correct your syntax errors", ex );
			throw Throwables.propagate( ex );
		}

		config.options().header( HEADER );
		config.options().copyDefaults( true );

		version = getInt( "config-version", 1 );
		set( "config-version", 1 );
		readConfig( UUIDServletConfig.class, null );

		Logger.getRootLogger().log( Level.INFO, "Configuration summary!" );
		for ( Field field : UUIDServletConfig.class.getDeclaredFields() ) {

			if ( Modifier.isPublic( field.getModifiers() ) && Modifier.isStatic( field.getModifiers() ) ) {

				field.setAccessible( true );
				try {
					Logger.getRootLogger()
							.log( Level.INFO, "    | " + field.getName() + " | " + field.getType() + " | " + field.get( null )
									.toString() );

				} catch ( IllegalAccessException e ) {
					Logger.getRootLogger().log( Level.FATAL, "Error reporting " + field, e );
				}

			}

		}

		config = null;

	}

	static void readConfig( Class<?> clazz, Object instance )
	{
		for ( Method method : clazz.getDeclaredMethods() ) {
			if ( Modifier.isPrivate( method.getModifiers() ) ) {
				if ( method.getParameterTypes().length == 0 && method.getReturnType() == Void.TYPE ) {
					try {
						method.setAccessible( true );
						method.invoke( instance );
					} catch ( InvocationTargetException ex ) {
						throw Throwables.propagate( ex.getCause() );
					} catch ( Exception ex ) {
						Logger.getRootLogger().log( Level.FATAL, "Error invoking " + method, ex );
					}
				}
			}
		}

		try {
			config.save( CONFIG_FILE );
		} catch ( IOException ex ) {
			Logger.getRootLogger().log( Level.FATAL, "Could not save " + CONFIG_FILE, ex );
		}
	}

	private static void set( String path, Object val )
	{
		config.set( path, val );
	}

	private static long getLong( String path, long def )
	{
		config.addDefault( path, def );
		return config.getLong( path, config.getLong( path ) );
	}

	private static boolean getBoolean( String path, boolean def )
	{
		config.addDefault( path, def );
		return config.getBoolean( path, config.getBoolean( path ) );
	}

	private static int getInt( String path, int def )
	{
		config.addDefault( path, def );
		return config.getInt( path, config.getInt( path ) );
	}

	private static <T> List getList( String path, T def )
	{
		config.addDefault( path, def );
		return config.getList( path, config.getList( path ) );
	}

	private static String getString( String path, String def )
	{
		config.addDefault( path, def );
		return config.getString( path, config.getString( path ) );
	}

	private static double getDouble( String path, double def )
	{
		config.addDefault( path, def );
		return config.getDouble( path, config.getDouble( path ) );
	}

	private static void debug()
	{
		debug = getBoolean( "settings.debug", false );
	}

	private static void dataSettings()
	{

		if ( version < 1 ) {
			Logger.getRootLogger().log( Level.FATAL, "Oudated config, config is not configured!" );
			set( "settings.data.configLocation", "{HOME}/data/UUIDData.json" );
		}

		String confLoc = getString( "settings.data.configLocation", "{HOME}/data/UUIDData.json" );

		if ( System.getProperty( "catalina.base" ) != null ) {
			confLoc = confLoc.replace( "{HOME}", System.getProperty( "catalina.base" ) )
					.replace( "/", File.separator );
		}

		dataLocation = new File( confLoc );
		dataLocation.getParentFile().mkdirs();

	}

	private static void passwordSettings()
	{

		if ( version < 1 ) {
			Logger.getRootLogger().log( Level.FATAL, "Oudated config, password is not configured!" );
			set( "password", "password132" );
		}

		password = getString( "password", "password132" );

	}

}

package co.ryred.uuidservlet;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

/**
 * @author Cory Redmond
 *         Created by acech_000 on 15/09/2015.
 */
@AllArgsConstructor
public class User
{

	@Getter
	private final String name;
	@Getter
	private final String stringUUID;
	@Getter
	private String profile = null;
	@Getter
	private String reason = null;

	public User( String name, UUID uuid, String profile, String reason )
	{
		this( name, uuid.toString().replace( "-", "" ), profile, reason );
	}

	/**
	 * Converts a String to a UUID
	 *
	 * @param uuid The string to be converted
	 * @return The result
	 */
	public static UUID getUUIDFromString( String uuid )
	{
		return UUID.fromString( uuid.substring( 0, 8 ) + "-" + uuid.substring( 8, 12 ) + "-" + uuid.substring( 12, 16 ) + "-" + uuid.substring( 16, 20 ) + "-" + uuid.substring( 20, 32 ) );
	}

	public UUID getUUID()
	{

		if ( stringUUID == null ) return null;

		return getUUIDFromString( stringUUID.replace( "-", "" ) );

	}

}

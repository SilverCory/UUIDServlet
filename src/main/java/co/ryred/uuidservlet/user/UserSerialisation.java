package co.ryred.uuidservlet.user;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * @author Cory Redmond
 *         Created by acech_000 on 17/09/2015.
 */
public class UserSerialisation implements /*JsonDeserializer<User>,*/ JsonSerializer<User>
{
	/*@Override
	public User deserialize( JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext ) throws JsonParseException
	{

		JsonObject jobject = (JsonObject) json;

		return new User(
				jobject.get("name").getAsString(),
				jobject.get("stringUUID").getAsString(),
				jobject.get("profile").getAsString(),
				jobject.get("reason").getAsString()
			);

	}*/

	@Override
	public JsonElement serialize( User user, Type type, JsonSerializationContext jsonSerializationContext )
	{

		JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty( "name", user.getName() );
		jsonObject.addProperty( "stringUUID", user.getStringUUID() );
		jsonObject.addProperty( "profile", user.getProfile() );
		jsonObject.addProperty( "reason", user.getReason() );

		return jsonObject;

	}
}

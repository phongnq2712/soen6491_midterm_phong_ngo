import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Registry {

	private static final Registry instance = new Registry();

	public static Registry getInstance() {
		return instance;
	}

	private final Map<String, Zone> zoneMap;

	private Registry() {
		zoneMap = new ConcurrentHashMap<>();
	}

	public Zone addZone(Zone zone) {
		return zoneMap.put(zone.name(), zone);
	}

	public Zone getZone(String name) {
		return zoneMap.get(name);
	}
}

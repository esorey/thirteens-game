package game;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum PlayMode {
	FREE_CHOICE,
	SINGLES,
	DOUBLES,
	TRIPLES,
	QUADRUPLES,
	RUN4,
	RUN5,
	RUN6,
	RUN7,
	RUN8,
	RUN9,
	RUN10,
	RUN11,
	RUN12,
	RUN13;
	
	public static List<PlayMode> getNOfAKindModes() {
		return Arrays.stream(PlayMode.values()).filter(m -> m.isNOfAKind()).collect(Collectors.toList());
	}
	

	public static List<PlayMode> getRunModes() {
		return Arrays.stream(PlayMode.values()).filter(m -> m.isRun()).collect(Collectors.toList());
	}

	
	public boolean isNOfAKind() {
		return (!(this.name().contains("RUN"))) && (!(this.equals(PlayMode.FREE_CHOICE)));
	}
	
	public boolean isRun() {
		return this.name().contains("RUN");
	}

	
	public int getNOfAKindCount() throws Exception {
		switch(this) {
			case SINGLES :
				return 1;
			case DOUBLES :
				return 2;
			case TRIPLES :
				return 3;
			case QUADRUPLES :
				return 4;
			default :
				throw new Exception("Invalid playmode for method getNOfAKindCount()");
		}
	}
	
	
	public int getRunLength() throws Exception {
		switch(this) {
			case RUN4 :
				return 4;
			case RUN5 :
				return 5;
			case RUN6 :
				return 6;
			case RUN7 :
				return 7;
			case RUN8 :
				return 8;
			case RUN9 :
				return 9;
			case RUN10 :
				return 10;
			case RUN11 :
				return 11;
			case RUN12 :
				return 12;
			case RUN13 :
				return 13;
			default :
				throw new Exception("Invalid playmode for method getRunLength()");
		}
	}
}

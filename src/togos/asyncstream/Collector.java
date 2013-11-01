package togos.asyncstream;

import java.util.ArrayList;
import java.util.Collection;

public class Collector<T> implements StreamDestination<T, RuntimeException>
{
	public final Collection<T> collection;
	
	public Collector( Collection<T> c ) {
		this.collection = c;
	}
	
	public Collector() {
		this( new ArrayList<T>() );
	}

	@Override public void data( T value ) { collection.add(value); }
	@Override public void end() {}
}

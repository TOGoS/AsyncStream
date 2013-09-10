package togos.asyncstream;

import java.io.IOException;

/**
 * Wraps a StreamDestination as an Appendable.
 * Not thread safe.
 */
public class StreamAppendable implements Appendable
{
	protected final StreamDestination<char[]> sd;
	protected char[] buffer;
	
	public StreamAppendable( StreamDestination<char[]> sd ) {
		this.sd = sd;
	}
	
	protected void dealWith(Exception e) throws IOException {
		if( e instanceof IOException ) {
			throw (IOException)e;
		} else if( e instanceof InterruptedException ) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		} else if( e instanceof RuntimeException ) {
			throw (RuntimeException)e;
		} else {
			throw new RuntimeException(e);
		}
	}
	
	@Override public Appendable append(char c) throws IOException {
		if( buffer == null ) buffer = new char[1];
		buffer[0] = c;
		try {
			sd.data(buffer);
		} catch( Exception e ) {
			dealWith(e);
		}
		return this;
	}
	
	@Override public Appendable append(CharSequence csq) throws IOException {
		// Most of the time csq will already be a string.
		try {
			sd.data(csq.toString().toCharArray());
		} catch( Exception e ) {
			dealWith(e);
		}
		return this;
	}
	
	@Override public Appendable append( CharSequence csq, int start, int end ) throws IOException {
		return append( csq.subSequence(start, end) );
	}
}

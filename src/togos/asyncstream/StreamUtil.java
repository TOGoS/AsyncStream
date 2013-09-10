package togos.asyncstream;

import java.io.Closeable;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;

public final class StreamUtil
{
	private StreamUtil() { }
	
	public static void pipe( InputStream r, StreamDestination<byte[]> d, boolean closeOnEnd ) throws Exception {
		byte[] buffer = new byte[1024];
		int i;
		while( (i = r.read(buffer)) > 0 ) {
			d.data( Arrays.copyOf(buffer, i) );
		}
		if( closeOnEnd ) d.end();
	}
	
	public static void pipe( Reader r, StreamDestination<char[]> d, boolean closeOnEnd ) throws Exception {
		char[] buffer = new char[1024];
		int i;
		while( (i = r.read(buffer)) > 0 ) {
			d.data( Arrays.copyOf(buffer, i) );
		}
		if( closeOnEnd ) d.end();
	}
	
	public static void pipe( StreamSource<char[]> source, final Writer w, final boolean closeOnEnd ) {
		source.pipe( new StreamDestination<char[]>() {
			@Override public void data(char[] value) throws Exception {
				w.write(value);
			}
			@Override public void end() throws Exception {
				if( closeOnEnd ) w.close();
			}
		});
	}
	
	public static <T> void closeOnEnd( StreamSource<T> source, final Closeable closeable ) {
		source.pipe( new StreamDestination<T>() {
			@Override public void data(T value) throws Exception {};
			@Override public void end() throws Exception {
				closeable.close();
			}
		} );
	}
}

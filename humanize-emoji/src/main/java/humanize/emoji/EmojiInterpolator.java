package humanize.emoji;

import static humanize.text.util.InterpolationHelper.interpolate;
import humanize.spi.MessageFormat;
import humanize.text.util.Replacer;
import humanize.text.util.UnicodeInterpolator;

import java.util.regex.Pattern;

/**
 * <p>
 * Sexy and easy text interpolation of Unicode emoji code points and aliases.
 * </p>
 * <h5>Examples:</h5>
 * 
 * <pre>
 * EmojiInterpolator.interpolateAlias(&quot;&lt;img src=\&quot;imgs/{0}.png\&quot; title=\&quot;{1}\&quot; /&gt;&quot;, &quot;Hi :sparkles:!&quot;);
 * // == &quot;Hi &lt;img src=\&quot;imgs/2728.png\&quot; title=\&quot;sparkles\&quot; /&gt;!&quot;
 * 
 * EmojiInterpolator.interpolateUnicode(&quot;&lt;img src=\&quot;imgs/{0}.png\&quot; /&gt;&quot;,
 *         &quot;Lorem ipsum \u2639 dolorem\uD83D\uDD36 and dolorem sit amet&quot;);
 * // ==
 * // &quot;Lorem ipsum &lt;img src=\&quot;imgs/2639.png\&quot; /&gt; dolorem&lt;img src=\&quot;imgs/1f536.png\&quot; /&gt; and dolorem sit amet&quot;
 * 
 * </pre>
 * 
 * @see Emoji
 * 
 */
public final class EmojiInterpolator
{

    private static final Pattern EMOJI_ALIAS = Pattern.compile(":([\\w\\s-]+):");

    public static String interpolateAlias(String pattern, String text)
    {
        return interpolate(text, EmojiInterpolator.EMOJI_ALIAS, new EmojiAliasReplacer(pattern));
    }

    public static String interpolateUnicode(String pattern, String text)
    {
        UnicodeInterpolator ui = new UnicodeInterpolator(new MessageFormatReplacer(pattern));
        ui.addRange(0x20a0, 0x32ff);
        ui.addRange(0x1f000, 0x1ffff);
        ui.addRange(0xfe4e5, 0xfe4ee);
        return ui.escape(text);
    }

    private EmojiInterpolator()
    {
        //
    }

    private final static class EmojiAliasReplacer extends MessageFormatReplacer
    {
        public EmojiAliasReplacer(String pattern)
        {
            super(pattern);
        }

        @Override
        public String replace(String alias)
        {
            // TODO better by de facto standard aliases?
            EmojiChar echar = Emoji.singleByAnnotations(alias);
            String code = echar == null ? alias : echar.getCode();

            return msgFormat.render(code, alias);
        }
    }

    private static class MessageFormatReplacer implements Replacer
    {
        protected final MessageFormat msgFormat;

        public MessageFormatReplacer(String pattern)
        {
            this.msgFormat = new MessageFormat(pattern);
        }

        @Override
        public String replace(String in)
        {
            return msgFormat.render(in);
        }
    }

}

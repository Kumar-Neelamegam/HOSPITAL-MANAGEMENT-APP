package kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import android.view.ViewGroup;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.styleable;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.extra.Scale;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.extra.TranslationTransition;

public class TransitionInflater {

    private static final Class<?>[] sConstructorSignature = new Class[] {
            Context.class, AttributeSet.class};
    private static final ArrayMap<String, Constructor> sConstructors =
            new ArrayMap<String, Constructor>();


    private final Context mContext;

    private TransitionInflater(Context context) {
        this.mContext = context;
    }

    /**
     * Obtains the TransitionInflater from the given context.
     */
    public static TransitionInflater from(Context context) {
        return new TransitionInflater(context);
    }

    /**
     * Loads a {@link Transition} object from a resource
     *
     * @param resource The resource id of the transition to load
     * @return The loaded Transition object
     * @throws NotFoundException when the
     *                                                         transition cannot be loaded
     */
    public Transition inflateTransition(int resource) {
        XmlResourceParser parser = this.mContext.getResources().getXml(resource);
        try {
            return this.createTransitionFromXml(parser, Xml.asAttributeSet(parser), null);
        } catch (XmlPullParserException e) {
            InflateException ex = new InflateException(e.getMessage(), e);
            throw ex;
        } catch (IOException e) {
            InflateException ex = new InflateException(
                    parser.getPositionDescription()
                            + ": " + e.getMessage(), e);
            throw ex;
        } finally {
            parser.close();
        }
    }

    /**
     * Loads a {@link TransitionManager} object from a resource
     *
     * @param resource The resource id of the transition manager to load
     * @return The loaded TransitionManager object
     * @throws NotFoundException when the
     *                                                         transition manager cannot be loaded
     */
    public TransitionManager inflateTransitionManager(int resource, ViewGroup sceneRoot) {
        XmlResourceParser parser = this.mContext.getResources().getXml(resource);
        try {
            return this.createTransitionManagerFromXml(parser, Xml.asAttributeSet(parser), sceneRoot);
        } catch (XmlPullParserException e) {
            InflateException ex = new InflateException(e.getMessage(), e);
            throw ex;
        } catch (IOException e) {
            InflateException ex = new InflateException(
                    parser.getPositionDescription()
                            + ": " + e.getMessage(), e);
            throw ex;
        } finally {
            parser.close();
        }
    }

    //
    // Transition loading
    //
    private Transition createTransitionFromXml(XmlPullParser parser,
                                               AttributeSet attrs, Transition parent)
            throws XmlPullParserException, IOException {

        Transition transition = null;

        // Make sure we are on a start tag.
        int type;
        int depth = parser.getDepth();

        TransitionSet transitionSet = (parent instanceof TransitionSet)
                ? (TransitionSet) parent : null;

        while (((type = parser.next()) != XmlPullParser.END_TAG || parser.getDepth() > depth)
                && type != XmlPullParser.END_DOCUMENT) {

            if (type != XmlPullParser.START_TAG) {
                continue;
            }

            String  name = parser.getName();
            if ("fade".equals(name)) {
                transition = new Fade(this.mContext, attrs);
            } else if ("changeBounds".equals(name)) {
                transition = new ChangeBounds(this.mContext, attrs);
            } else if ("slide".equals(name)) {
                transition = new Slide(this.mContext, attrs);
            } else if ("explode".equals(name)) {
                transition = new Explode(this.mContext, attrs);
            } else if ("changeImageTransform".equals(name)) {
                transition = new ChangeImageTransform(this.mContext, attrs);
            } else if ("changeTransform".equals(name)) {
                transition = new ChangeTransform(this.mContext, attrs);
            } else if ("changeClipBounds".equals(name)) {
                transition = new ChangeClipBounds(this.mContext, attrs);
            } else if ("autoTransition".equals(name)) {
                transition = new AutoTransition(this.mContext, attrs);
            } else if ("recolor".equals(name)) {
                transition = new Recolor(this.mContext, attrs);
            } else if ("changeScroll".equals(name)) {
                transition = new ChangeScroll(this.mContext, attrs);
            } else if ("transitionSet".equals(name)) {
                transition = new TransitionSet(this.mContext, attrs);
            } else if ("scale".equals(name)) {
                transition = new Scale(this.mContext, attrs);
            } else if ("translation".equals(name)) {
                transition = new TranslationTransition(this.mContext, attrs);
            } else if ("transition".equals(name)) {
                transition = (Transition) this.createCustom(attrs, Transition.class, "transition");
            } else if ("targets".equals(name)) {
                this.getTargetIds(parser, attrs, parent);
            } else if ("arcMotion".equals(name)) {
                parent.setPathMotion(new ArcMotion(this.mContext, attrs));
            } else if ("pathMotion".equals(name)) {
                parent.setPathMotion((PathMotion) this.createCustom(attrs, PathMotion.class, "pathMotion"));
            } else if ("patternPathMotion".equals(name)) {
                parent.setPathMotion(new PatternPathMotion(this.mContext, attrs));
            } else {
                throw new RuntimeException("Unknown scene name: " + parser.getName());
            }
            if (transition != null) {
                if (!parser.isEmptyElementTag()) {
                    this.createTransitionFromXml(parser, attrs, transition);
                }
                if (transitionSet != null) {
                    transitionSet.addTransition(transition);
                    transition = null;
                } else if (parent != null) {
                    throw new InflateException("Could not add transition to another transition.");
                }
            }
        }

        return transition;
    }

    private Object createCustom(AttributeSet attrs, Class expectedType, String tag) {
        String className = attrs.getAttributeValue(null, "class");

        if (className == null) {
            throw new InflateException(tag + " tag must have a 'class' attribute");
        }

        try {
            synchronized (TransitionInflater.sConstructors) {
                Constructor constructor = TransitionInflater.sConstructors.get(className);
                if (constructor == null) {
                    Class c = this.mContext.getClassLoader().loadClass(className)
                            .asSubclass(expectedType);
                    if (c != null) {
                        constructor = c.getConstructor(TransitionInflater.sConstructorSignature);
                        if (!constructor.isAccessible()) {
                            constructor.setAccessible(true);
                        }
                        TransitionInflater.sConstructors.put(className, constructor);
                    }
                }

                return constructor.newInstance(this.mContext, attrs);
            }
        } catch (InstantiationException e) {
            throw new InflateException("Could not instantiate " + expectedType + " class " +
                    className, e);
        } catch (ClassNotFoundException e) {
            throw new InflateException("Could not instantiate " + expectedType + " class " +
                    className, e);
        } catch (InvocationTargetException e) {
            throw new InflateException("Could not instantiate " + expectedType + " class " +
                    className, e);
        } catch (NoSuchMethodException e) {
            throw new InflateException("Could not instantiate " + expectedType + " class " +
                    className, e);
        } catch (IllegalAccessException e) {
            throw new InflateException("Could not instantiate " + expectedType + " class " +
                    className, e);
        }
    }

    private void getTargetIds(XmlPullParser parser,
                              AttributeSet attrs, Transition transition) throws XmlPullParserException, IOException {

        // Make sure we are on a start tag.
        int type;
        int depth = parser.getDepth();

        while (((type=parser.next()) != XmlPullParser.END_TAG || parser.getDepth() > depth)
                && type != XmlPullParser.END_DOCUMENT) {

            if (type != XmlPullParser.START_TAG) {
                continue;
            }

            String  name = parser.getName();
            if (name.equals("target")) {
                TypedArray a = this.mContext.obtainStyledAttributes(attrs, styleable.TransitionTarget);
                int id = a.getResourceId(styleable.TransitionTarget_targetId, 0);
                String transitionName;
                if (id != 0) {
                    transition.addTarget(id);
                } else if ((id = a.getResourceId(styleable.TransitionTarget_excludeId, 0)) != 0) {
                    transition.excludeTarget(id, true);
                } else if ((transitionName = a.getString(styleable.TransitionTarget_targetName))
                        != null) {
                    transition.addTarget(transitionName);
                } else if ((transitionName = a.getString(styleable.TransitionTarget_excludeName))
                        != null) {
                    transition.excludeTarget(transitionName, true);
                } else {
                    String className = a.getString(styleable.TransitionTarget_excludeClass);
                    try {
                        if (className != null) {
                            Class clazz = Class.forName(className);
                            transition.excludeTarget(clazz, true);
                        } else if ((className =
                                a.getString(styleable.TransitionTarget_targetClass)) != null) {
                            Class clazz = Class.forName(className);
                            transition.addTarget(clazz);
                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException("Could not create " + className, e);
                    }
                }
                a.recycle();
            } else {
                throw new RuntimeException("Unknown scene name: " + parser.getName());
            }
        }
    }

    //
    // TransitionManager loading
    //
    private TransitionManager createTransitionManagerFromXml(XmlPullParser parser,
                                                             AttributeSet attrs, ViewGroup sceneRoot) throws XmlPullParserException, IOException {

        // Make sure we are on a start tag.
        int type;
        int depth = parser.getDepth();
        TransitionManager transitionManager = null;

        while (((type = parser.next()) != XmlPullParser.END_TAG || parser.getDepth() > depth)
                && type != XmlPullParser.END_DOCUMENT) {

            if (type != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            if (name.equals("transitionManager")) {
                transitionManager = new TransitionManager();
            } else if (name.equals("transition") && (transitionManager != null)) {
                this.loadTransition(attrs, sceneRoot, transitionManager);
            } else {
                throw new RuntimeException("Unknown scene name: " + parser.getName());
            }
        }
        return transitionManager;
    }

    private void loadTransition(AttributeSet attrs, ViewGroup sceneRoot,
                                TransitionManager transitionManager) throws NotFoundException {

        TypedArray a = this.mContext.obtainStyledAttributes(attrs, styleable.TransitionManager);
        int transitionId = a.getResourceId(styleable.TransitionManager_transition, -1);
        int fromId = a.getResourceId(styleable.TransitionManager_fromScene, -1);
        Scene fromScene = (fromId < 0) ? null: Scene.getSceneForLayout(sceneRoot, fromId, this.mContext);
        int toId = a.getResourceId(styleable.TransitionManager_toScene, -1);
        Scene toScene = (toId < 0) ? null : Scene.getSceneForLayout(sceneRoot, toId, this.mContext);

        if (transitionId >= 0) {
            Transition transition = this.inflateTransition(transitionId);
            if (transition != null) {
                if (toScene == null) {
                    throw new RuntimeException("No toScene for transition ID " + transitionId);
                }
                if (fromScene == null) {
                    transitionManager.setTransition(toScene, transition);
                } else {
                    transitionManager.setTransition(fromScene, toScene, transition);
                }
            }
        }
        a.recycle();
    }
}

/*
 * Copyright (c) 2015 Chris Newland.
 * Licensed under https://github.com/chriswhocodes/demofx/blob/master/LICENSE-BSD
 */
package com.chrisnewland.demofx;

public class DemoConfig
{
	public enum PlotMode
	{
		PLOT_MODE_LINE, PLOT_MODE_POLYGON, PLOT_MODE_FILL_POLYGON
	}

	private String effect = "stars";
	private int count = -1;
	private int rotation = 10;
	private int width = 800;
	private int height = 600;
	private boolean antialias = false;

	private boolean lookupSqrt = false;
	private boolean lookupTrig = true;
	private boolean lookupRandom = true;

	private PlotMode plotMode = PlotMode.PLOT_MODE_FILL_POLYGON;

	private DemoConfig()
	{
	}

	public static String getUsageError()
	{
		StringBuilder builder = new StringBuilder();

		builder.append("DemoFXApplication [options]").append("\n");
		builder.append(buildUsageLine("-e <effect>", "triangles,squares,pentagons,hexagons,stars,rings,tiles,spin"));
		builder.append(buildUsageLine("-c <count>", "number of items on screen"));
		builder.append(buildUsageLine("-r <degrees>", "rotation per frame"));
		builder.append(buildUsageLine("-w <width>", "canvas width"));
		builder.append(buildUsageLine("-h <height>", "canvas height"));
		builder.append(buildUsageLine("-l [sqrt,trig,rand,none]", "use lookup tables for Math.sqrt, Math.sin/Math.cos, Math.Random"));
		builder.append(buildUsageLine("-a <true|false>", "antialias canvas"));
		builder.append(buildUsageLine("-m <line|poly|fill>", "canvas plot mode"));

		return builder.toString();
	}

	private static String buildUsageLine(String left, String right)
	{
		int leftWidth = 27;

		StringBuilder builder = new StringBuilder();

		builder.append(left);

		for (int i = 0; i < leftWidth - left.length(); i++)
		{
			builder.append(" ");
		}

		builder.append(right);

		builder.append("\n");

		return builder.toString();
	}

	public static DemoConfig parseArgs(String[] args)
	{
		DemoConfig config = new DemoConfig();

		boolean argError = false;

		int argc = args.length;

		for (int i = 0; i < argc; i += 2)
		{
			String arg = args[i];

			boolean lastArg = (i == argc - 1);

			if (arg.startsWith("-") && arg.length() == 2 && !lastArg)
			{
				String value = args[i + 1];

				try
				{
					switch (arg.substring(1))
					{
					case "e":
						config.effect = value;
						break;
					case "c":
						config.count = Integer.parseInt(value);
						break;
					case "w":
						config.width = Integer.parseInt(value);
						break;
					case "h":
						config.height = Integer.parseInt(value);
						break;
					case "r":
						config.rotation = Integer.parseInt(value);
						break;
					case "a":
						config.antialias = Boolean.parseBoolean(value);
						break;
					case "l":
						checkLookupOptions(config, value);
						break;
					case "m":
						if ("line".equals(value.toLowerCase()))
						{
							config.plotMode = PlotMode.PLOT_MODE_LINE;
						}
						else if ("poly".equals(value.toLowerCase()))
						{
							config.plotMode = PlotMode.PLOT_MODE_POLYGON;
						}
						else if ("fill".equals(value.toLowerCase()))
						{
							config.plotMode = PlotMode.PLOT_MODE_FILL_POLYGON;
						}
						else
						{
							argError = true;
						}
						break;
					default:
						argError = true;
						break;
					}
				}
				catch (Exception e)
				{
					argError = true;
					break;
				}
			}
			else
			{
				argError = true;
			}
		}

		if (argError)
		{
			config = null;
		}

		return config;
	}

	private static void checkLookupOptions(DemoConfig config, String value)
	{
		if (value.toLowerCase().contains("none"))
		{
			config.lookupSqrt = false;
			config.lookupTrig = false;
			config.lookupRandom = false;
		}

		if (value.toLowerCase().contains("sqrt"))
		{
			config.lookupSqrt = true;
		}

		if (value.toLowerCase().contains("trig"))
		{
			config.lookupTrig = true;
		}

		if (value.toLowerCase().contains("rand"))
		{
			config.lookupRandom = true;
		}
	}

	public String getEffect()
	{
		return effect.substring(0, 1).toUpperCase() + effect.substring(1).toLowerCase();
	}

	public int getCount()
	{
		return count;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public boolean isAntialias()
	{
		return antialias;
	}

	public PlotMode getPlotMode()
	{
		return plotMode;
	}

	public int getRotation()
	{
		return rotation;
	}

	public boolean isLookupSqrt()
	{
		return lookupSqrt;
	}

	public boolean isLookupTrig()
	{
		return lookupTrig;
	}

	public boolean isLookupRandom()
	{
		return lookupRandom;
	}
}
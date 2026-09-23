package dev.emi.emi.jemi.impl.extras;

import java.util.Collection;
import java.util.List;

import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.placement.VerticalAlignment;
import mezz.jei.api.gui.widgets.IRecipeWidgetTooltipCallback;
import mezz.jei.api.gui.widgets.ITextWidget;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.item.TooltipData;
import net.minecraft.text.StringVisitable;

public class JemiTextWidget extends JemiPlaceable<ITextWidget> implements ITextWidget {
	public int color = 0xffffffff;
	public boolean shadow = true;
	public int spacing = 0;
	public HorizontalAlignment horizontal = HorizontalAlignment.LEFT;
	public VerticalAlignment vertical = VerticalAlignment.TOP;
	public List<StringVisitable> text;
	public IRecipeWidgetTooltipCallback tooltipCallback;

	public JemiTextWidget(List<StringVisitable> text, int width, int height) {
		super(width, height);
		this.text = text;
	}

	@Override
	public ITextWidget setFont(TextRenderer font) {
		// Unimplemented
		return this;
	}

	@Override
	public ITextWidget setColor(int color) {
		this.color = color;
		return this;
	}

	@Override
	public ITextWidget setLineSpacing(int spacing) {
		this.spacing = spacing;
		return this;
	}

	@Override
	public ITextWidget setShadow(boolean shadow) {
		this.shadow = shadow;
		return this;
	}

	@Override
	public ITextWidget setTooltip(StringVisitable tooltip) {
		this.tooltipCallback = builder -> builder.add(tooltip);
		return this;
	}

	@Override
	public ITextWidget setTooltip(Collection<? extends StringVisitable> tooltip) {
		List<? extends StringVisitable> copy = List.copyOf(tooltip);
		this.tooltipCallback = builder -> builder.addAll(copy);
		return this;
	}

	@Override
	public ITextWidget setTooltip(TooltipData tooltip) {
		this.tooltipCallback = builder -> builder.add(tooltip);
		return this;
	}

	@Override
	public ITextWidget setTooltip(IRecipeWidgetTooltipCallback tooltipCallback) {
		this.tooltipCallback = tooltipCallback;
		return this;
	}

	@Override
	public ITextWidget setTextAlignment(HorizontalAlignment horizontalAlignment) {
		this.horizontal = horizontalAlignment;
		return this;
	}

	@Override
	public ITextWidget setTextAlignment(VerticalAlignment verticalAlignment) {
		this.vertical = verticalAlignment;
		return this;
	}
	
}

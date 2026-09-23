package dev.emi.emi.jemi.impl.extras;

import java.util.Collection;
import java.util.List;

import dev.emi.emi.api.widget.WidgetHolder;
import dev.emi.emi.jemi.impl.JemiTooltipBuilder;
import mezz.jei.api.gui.widgets.IDrawableWidget;
import mezz.jei.api.gui.widgets.IRecipeWidgetTooltipCallback;
import net.minecraft.client.item.TooltipData;
import net.minecraft.text.StringVisitable;

public class JemiDrawableWidget extends JemiPlaceable<IDrawableWidget> implements IDrawableWidget {
	public WidgetConstructor constructor;
	private IRecipeWidgetTooltipCallback tooltipCallback;

	public JemiDrawableWidget(int width, int height, WidgetConstructor constructor) {
		super(width, height);
		this.constructor = constructor;
	}

	public void addWidgets(WidgetHolder holder) {
		constructor.accept(this, holder);
		if (tooltipCallback != null) {
			holder.addTooltip((mouseX, mouseY) -> {
				JemiTooltipBuilder builder = new JemiTooltipBuilder();
				tooltipCallback.onTooltip(builder);
				return builder.tooltip;
			}, x, y, width, height);
		}
	}

	@Override
	public IDrawableWidget setTooltip(StringVisitable tooltip) {
		this.tooltipCallback = builder -> builder.add(tooltip);
		return this;
	}

	@Override
	public IDrawableWidget setTooltip(Collection<? extends StringVisitable> tooltip) {
		List<? extends StringVisitable> copy = List.copyOf(tooltip);
		this.tooltipCallback = builder -> builder.addAll(copy);
		return this;
	}

	@Override
	public IDrawableWidget setTooltip(TooltipData tooltip) {
		this.tooltipCallback = builder -> builder.add(tooltip);
		return this;
	}

	@Override
	public IDrawableWidget setTooltip(IRecipeWidgetTooltipCallback tooltipCallback) {
		this.tooltipCallback = tooltipCallback;
		return this;
	}

	@FunctionalInterface
	public interface WidgetConstructor {
		void accept(JemiDrawableWidget builder, WidgetHolder holder);
	}
}

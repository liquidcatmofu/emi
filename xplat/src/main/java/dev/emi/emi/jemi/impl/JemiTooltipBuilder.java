package dev.emi.emi.jemi.impl;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;

import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.runtime.IJeiKeyMapping;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipData;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;

public class JemiTooltipBuilder implements ITooltipBuilder {
	public final List<TooltipComponent> tooltip = Lists.newArrayList();
	private final List<Text> legacyText = Lists.newArrayList();
	private final List<Either<StringVisitable, TooltipData>> lines = Lists.newArrayList();

	@Override
	public void add(StringVisitable component) {
		if (component == null) {
			return;
		}
		lines.add(Either.left(component));
		if (component instanceof Text text) {
			tooltip.add(TooltipComponent.of(text.asOrderedText()));
			legacyText.add(text);
		}
	}

	@Override
	public void addAll(Collection<? extends StringVisitable> components) {
		for (StringVisitable component : components) {
			add(component);
		}
	}

	@Override
	public void add(TooltipData data) {
		if (data == null) {
			return;
		}
		lines.add(Either.right(data));
		try {
			tooltip.add(TooltipComponent.of(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addKeyUsageComponent(String translationKey, IJeiKeyMapping keyMapping) {
		add(Text.translatable(translationKey, keyMapping.getTranslatedKeyMessage()));
	}

	@Override
	public void setIngredient(ITypedIngredient<?> typedIngredient) {
		// EMI's tooltip path does not pass a backing JEI ingredient to vanilla events.
	}

	@Override
	public void clear() {
		lines.clear();
		tooltip.clear();
		legacyText.clear();
	}

	@Override
	public void clearIngredient() {
		// No ingredient state is stored by this adapter.
	}

	@Override
	public List<Either<StringVisitable, TooltipData>> getLines() {
		return lines;
	}

	@Override
	public List<Text> toLegacyToComponents() {
		return legacyText;
	}

	@Override
	public void removeAll(List<Text> components) {
		legacyText.removeAll(components);
		lines.removeIf(line -> line.left().filter(components::contains).isPresent());
		rebuildTooltip();
	}

	private void rebuildTooltip() {
		tooltip.clear();
		for (Either<StringVisitable, TooltipData> line : lines) {
			line.left().ifPresent(component -> {
				if (component instanceof Text text) {
					tooltip.add(TooltipComponent.of(text.asOrderedText()));
				}
			});
			line.right().ifPresent(data -> tooltip.add(TooltipComponent.of(data)));
		}
	}
}

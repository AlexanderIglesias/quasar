package com.mercadolibre.challenge.quasar.data.utils;

import org.springframework.stereotype.Service;

import com.mercadolibre.challenge.quasar.data.dto.PositionDTO;
import com.mercadolibre.challenge.quasar.data.dto.SatelliteDTO;

@Service
public class MathUtils {

	public double distance(PositionDTO p1, PositionDTO p2) {

		double x = Math.pow(
				(p1.getX() != null ? p1.getX().intValue() : 0) - (p2.getX() != null ? p2.getX().intValue() : 0), 2);
		double y = Math.pow(
				(p1.getY() != null ? p1.getY().intValue() : 0) - (p2.getY() != null ? p2.getY().intValue() : 0), 2);

		return Math.sqrt(x + y);
	}
	
	
	public PositionDTO calculatePosition(SatelliteDTO kenobi, SatelliteDTO sato, SatelliteDTO skyWalker) {
		Float x1 = kenobi.getPosition().getX();
		Float y1 = kenobi.getPosition().getY();
		Float d1 = kenobi.getDistance();

		Float x2 = skyWalker.getPosition().getX();
		Float y2 = skyWalker.getPosition().getY();
		Float d2 = skyWalker.getDistance();

		Float x3 = sato.getPosition().getX();
		Float y3 = sato.getPosition().getY();
		Float d3 = sato.getDistance();

		Float a = 2 * x2 - 2 * x1;
		Float b = 2 * y2 - 2 * y1;
		Float c = Float.parseFloat((Math.pow(d1, 2) - Math.pow(d2, 2) - Math.pow(x1, 2) + Math.pow(x2, 2)
				- Math.pow(y1, 2) + Math.pow(y2, 2)) + "");
		Float d = 2 * x3 - 2 * x2;
		Float e = 2 * y3 - 2 * y2;
		Float f = Float.parseFloat((Math.pow(d2, 2) - Math.pow(d3, 2) - Math.pow(x2, 2) + Math.pow(x3, 2)
				- Math.pow(y2, 2) + Math.pow(y3, 2)) + "");

		PositionDTO p = new PositionDTO();

		p.setX((c * e - f * b) / (e * a - b * d));
		p.setY((c * d - a * f) / (b * d - a * e));
		return p;
	}

}

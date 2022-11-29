package xyz.rpletsgo.tagihan.core;

import xyz.rpletsgo.common.core.IFinancialEventFactory;
import xyz.rpletsgo.tagihan.model.Tagihan;

public interface ITagihanFactory extends IFinancialEventFactory {
    @Override
    Tagihan create();
}

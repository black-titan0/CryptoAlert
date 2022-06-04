package consume_procedures;

import kafka_utilities.CandleConsumer;

public interface ConsumeProcedure extends Runnable{
    ConsumeProcedure introduceConsumer(CandleConsumer consumer, String targetMarket);
    void stop();

}

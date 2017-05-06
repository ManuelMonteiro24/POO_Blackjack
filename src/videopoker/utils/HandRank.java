package videopoker.utils;

/* 0-> nothing 1-> joB 2 -> Two Pair 3-> ToaK 4 -> Straight 5-> Flush 6->FH(pair and a ToaK) 7->FoaK(5-K) 8-> FoaK(2-4) 9->Foak(A) 10->SF 11->RF*/
public enum HandRank { NON, PAIR, JoB, TWOPAIR, TOAK, STRAIGHT, FLUSH, FULLHOUSE, FOAK_5K, FOAK_24, FOAK_A, STRAIGHT_FLUSH, ROYAL_FLUSH }
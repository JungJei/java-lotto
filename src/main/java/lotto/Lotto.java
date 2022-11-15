package lotto;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.*;

import static lotto.Lotto.Ranking.getRank;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException();
        }
    }

    // TODO: 추가 기능 구현
    public List<Integer>[] drawLottoNumbers(int lottoCount) {
        List<Integer>[] lottoNumbers = new ArrayList[lottoCount];
        for (int i = 0; i < lottoCount; i++) {
            lottoNumbers[i] = new ArrayList<>();
            lottoNumbers[i] = Randoms.pickUniqueNumbersInRange(1, 45, 6);
            Collections.sort(lottoNumbers[i]);
        }
        return lottoNumbers;
    }

    public void compareLotto(List<Integer> numbers, List<Integer>[] lottoNumbers, int bonusNumber) {
        int count;
        boolean bonus;
        List<Integer> countWinning = List.of(0, 0, 0, 0, 0);

        for (int i = 0; i < lottoNumbers.length; i++) {
            for (int j = 0; j < numbers.size(); j++) {
                count = compareNumbers(numbers.get(j), lottoNumbers[i]);
                bonus = compareBonusNumber(lottoNumbers[i], bonusNumber);
                Ranking ranking = Ranking.getRank(count, bonus);
                countWinning(ranking, countWinning);
            }
        }
    }
    public int compareNumbers(int numbers, List<Integer> lottoNumbers) {
        int count = 0;
        for (int i = 0; i < lottoNumbers.size(); i++) {
            if (numbers == lottoNumbers.get(i)) {
                count++;
            }
        }
        return count;
    }
    public boolean compareBonusNumber(List<Integer> lottoNumbers, int bonus) {
        for (int i = 0; i < lottoNumbers.size(); i++) {
            if (bonus == lottoNumbers.get(i)) {
                return true;
            }
        }
        return false;
    }
    public void countWinning(Ranking ranking, List<Integer> countWinning) {
        if (ranking != null) {
            countWinning.set(ranking.getIndex(), countWinning.get(ranking.getIndex()) + 1);
        }
    }

    public enum Ranking {
        THREE(0, 3, false, 5000),
        FOUR(1, 4, false, 50_000),
        FIVE(2, 5, false, 1_500_000),
        BONUS(3, 5, true, 30_000_000),
        SIX(4, 6, false, 2_000_000_000);

        private final int index;
        private final int rightNumber;
        private final boolean bonus;
        private final int prize;

        Ranking(int index, int rightNumber, boolean bonus, int prize) {
            this.index = index;
            this.rightNumber = rightNumber;
            this.bonus = bonus;
            this.prize = prize;
        }

        public int getIndex() {
            return this.index;
        }

        public static Ranking getRank(int countWinner, boolean bonus) {
            return Arrays.stream(values())
                    .filter(ranking -> ranking.rightNumber == countWinner)
                    .filter(ranking -> ranking.bonus == bonus)
                    .findAny()
                    .orElseThrow();
        }
    }
}
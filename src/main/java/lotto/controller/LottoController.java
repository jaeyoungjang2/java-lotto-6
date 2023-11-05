package lotto.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lotto.domain.Lotto;
import lotto.domain.LottoNumber;
import lotto.domain.Rank;
import lotto.domain.Ranks;
import lotto.service.LottoService;

public class LottoController {

    public List<Lotto> buyLottery(String stringPurchasePrice) {
        Integer purchasePrice = validatePurchasePrice(stringPurchasePrice);

        LottoService lottoService = new LottoService();
        return lottoService.buyLottery(purchasePrice);
    }

    private Integer validatePurchasePrice(String stringPurchasePrice) {
        validatePurchasePriceType(stringPurchasePrice);
        Integer purchasePrice = Integer.valueOf(stringPurchasePrice);
        validatePurchasePriceRange(purchasePrice);
        return purchasePrice;
    }

    private void validatePurchasePriceRange(Integer purchasePrice) {
        if (purchasePrice % 1_000 != 0) {
            throw new IllegalArgumentException("로또는 1000원 단위로만 구매할 수 있습니다.");
        }
    }

    private void validatePurchasePriceType(String purchasePrice) {
        try {
            Integer.valueOf(purchasePrice);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("로또 금액은 숫자만 입력 가능합니다.");
        }
    }

    public Ranks lottoResults(List<Lotto> lottery, String stringWinningNumber, LottoNumber bonusNumber) {
        validateWinningNumber(stringWinningNumber);

        List<Integer> winningNumber = Arrays.stream(stringWinningNumber.split(","))
                .map(s -> Integer.valueOf(s))
                .collect(Collectors.toList());
        LottoService lottoService = new LottoService();
        return lottoService.lottoResults(
                lottery, winningNumber, bonusNumber);
    }

    private void validateWinningNumber(String winningNumber) {
        String[] split = winningNumber.split(",");
        validateLength(split);
        validateType(split);
    }

    private static void validateType(String[] split) {
        try {
            for (String s : split) {
                Integer.valueOf(s);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("로또 금액은 숫자만 입력 가능합니다2.");
        }
    }

    private static void validateLength(String[] split) {
        if (split.length != 6) {
            throw new IllegalArgumentException("당첩 번호는 6개의 숫자로 입력되어야 합니다.");
        }
    }
}

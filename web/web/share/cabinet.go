package share

import (
	"fmt"

	"github.com/TypingHare/burrow/v2026/burrow/larder"
	"github.com/TypingHare/burrow/v2026/burrow/larder/share"
	larderShare "github.com/TypingHare/burrow/v2026/burrow/larder/share"
	"github.com/TypingHare/burrow/v2026/kernel"
)

const WebCabinetName = "web"

func GetWebCabinet(chamber *kernel.Chamber) (*share.Cabinet[WebRecord], error) {
	larderDecoration, err := larder.UseLarderDecoration(chamber)
	if err != nil {
		return nil, fmt.Errorf("failed to get larder decoration: %w", err)
	}

	cabinet, err := larderShare.GetCabinet[WebRecord](
		larderDecoration,
		WebCabinetName,
	)
	if err != nil {
		return nil, fmt.Errorf("failed to get web cabinet: %w", err)
	}

	return cabinet, nil
}

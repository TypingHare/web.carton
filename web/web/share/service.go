package share

import (
	"fmt"
	"slices"

	coreShare "github.com/TypingHare/burrow/v2026/burrow/core/share"
	"github.com/TypingHare/burrow/v2026/kernel"
)

func GetWebRecordByName(
	d kernel.DecorationInstance,
	name string,
) (*WebRecord, error) {
	cabinet, err := GetWebCabinet(d)
	if err != nil {
		return nil, fmt.Errorf("failed to get web cabinet: %w", err)
	}

	for _, webRecord := range cabinet.Objects {
		if webRecord.Name == name {
			return &webRecord, nil
		}
	}

	return nil, fmt.Errorf("web record with name %q not found", name)
}

func OpenWeb(d kernel.DecorationInstance, name string) error {
	webRecord, err := GetWebRecordByName(d, name)
	if err != nil {
		return fmt.Errorf("failed to get web record by name: %w", err)
	}

	_, stderr, exitCode, err := coreShare.RunExternalCommand(
		"",
		[]string{"open", "https://" + webRecord.URL},
	)
	if err != nil || exitCode != 0 {
		return fmt.Errorf(
			"failed to execute open command: %w",
			fmt.Errorf("%s: %w", err.Error(),
				fmt.Errorf("%s", stderr),
			),
		)
	}

	return nil
}

func DeleteWeb(d kernel.DecorationInstance, name string) error {
	cabinet, err := GetWebCabinet(d)
	if err != nil {
		return fmt.Errorf("failed to get web cabinet: %w", err)
	}

	index := slices.IndexFunc(cabinet.Objects, func(webRecord WebRecord) bool {
		return webRecord.Name == name
	})
	if index == -1 {
		return fmt.Errorf("web record with name %q not found", name)
	}

	cabinet.Objects = slices.Delete(cabinet.Objects, index, index+1)

	return nil
}

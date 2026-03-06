package web

import (
	"fmt"

	"github.com/TypingHare/burrow/v2026/burrow/core"
	"github.com/TypingHare/burrow/v2026/burrow/larder"
	larderShare "github.com/TypingHare/burrow/v2026/burrow/larder/share"
	"github.com/TypingHare/burrow/v2026/kernel"
	"github.com/TypingHare/web.carton/v2026/web/web/command"
	"github.com/TypingHare/web.carton/v2026/web/web/share"
)

type WebDecoration struct {
	kernel.Decoration[share.WebSpec]
}

func (d *WebDecoration) RawSpec() kernel.RawSpec {
	return kernel.RawSpec{
		"autoDispatch":     d.Spec().AutoDispatch,
		"silentlyDispatch": d.Spec().SilentlyDispatch,
	}
}

func (d *WebDecoration) Dependencies() []string {
	return []string{
		kernel.GetDecorationID("larder", kernel.CartonName),
	}
}

func (d *WebDecoration) Assemble() error {
	coreDecoration, err := core.UseDecoration(d)
	if err != nil {
		return fmt.Errorf("failed to get core decoration: %w", err)
	}
	coreDecoration.MergeCommand(nil, command.ListCommand(d))

	larderDecoration, err := kernel.Use[*larder.LarderDecoration](d.Chamber())
	if err != nil {
		return fmt.Errorf("failed to get larder decoration: %w", err)
	}

	_, err = larderShare.AddCabinet(
		larderDecoration,
		share.WebCabinetName,
		share.SerializeWebRecord,
		share.DeserializeWebRecord,
	)
	if err != nil {
		return fmt.Errorf("failed to add cabinet: %w", err)
	}

	return nil
}

func (d *WebDecoration) Launch() error {
	cabinet, err := share.GetWebCabinet(d.Chamber())
	if err != nil {
		return fmt.Errorf("failed to get web cabinet: %w", err)
	}
	cabinet.Load()

	return nil
}

func (d *WebDecoration) Terminate() error {
	cabinet, err := share.GetWebCabinet(d.Chamber())
	if err != nil {
		return fmt.Errorf("failed to get web cabinet: %w", err)
	}
	cabinet.Save()

	return nil
}
func (d *WebDecoration) Disassemble() error { return nil }

func BuildWebDecoration(
	chamber *kernel.Chamber,
	spec share.WebSpec,
) (kernel.DecorationInstance, error) {
	return &WebDecoration{
		Decoration: *kernel.NewDecoration(chamber, spec),
	}, nil
}
